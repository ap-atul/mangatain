package com.atul.mangatain.generator;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Generator<T> implements AutoCloseable, Iterable<T>, Iterator<T>, Runnable {

    enum WhoHasTheBall {FOREGROUND, BACKGROUND};
    private volatile WhoHasTheBall whoHasTheBall = WhoHasTheBall.FOREGROUND;

    private final Object syncObj = new Object();

    /** The background thread. */
    private final Thread thread;

    /** The next value that is ready. */
    private T nextValue = null;

    /** Used to determine if the background thread needs to quit. */
    private volatile boolean isThreadCancelled = false;

    /** Is foreground process cancelled. */
    private volatile boolean isClosed = false;


    public Generator() {
        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void close() {
        if (Thread.currentThread() == thread) {
            // Background thread is calling 'close()'.
            if (!isThreadCancelled) {
                synchronized (syncObj) {
                    isThreadCancelled = true;
                    syncObj.notifyAll();
                }
            }
        } else {
            // Foreground thread is calling 'close()'.
            if (!isClosed) {
                isClosed = true;

                if (!isThreadCancelled) {
                    synchronized (syncObj) {
                        isThreadCancelled = true;
                        syncObj.notifyAll();
                    }
                }
            }
        }
    }


    @NonNull
    @Override
    public Iterator<T> iterator() {
        return this;
    }


    @Override
    public boolean hasNext() {
        if (isClosed) {
            return false;
        }

        boolean result = false;

        synchronized (syncObj) {
            if (whoHasTheBall == WhoHasTheBall.FOREGROUND) {
                whoHasTheBall = WhoHasTheBall.BACKGROUND;
                syncObj.notifyAll();
            }
            while (whoHasTheBall != WhoHasTheBall.FOREGROUND) {
                if (isClosed || isThreadCancelled) {
                    // If 'close()' has been called then drop out of this loop and return false.
                    // If the background thread has finished then don't wait for anything else
                    //  to be added by yieldReturn(...).
                    break;
                }

                try {
                    // Wait for the background thread to return control to the foreground.
                    syncObj.wait();
                } catch (InterruptedException ex) {
                    // Ignore the InterruptedException and keep looping.
                }
            }

            result = !isClosed && !isThreadCancelled;
        }//synchronized

        return result;
    }


    @Override
    public T next() {
        if (isClosed) {
            throw new NoSuchElementException();
        }

        T tmp = nextValue;
        nextValue = null;
        return tmp;
    }


    @Override
    public void run() {
        try {
            synchronized (syncObj) {
                //---------------------------------------------------------------------------------------
                // Wait for the first time for the foreground thread to call 'hasNext()', then proceed.
                //---------------------------------------------------------------------------------------
                while (whoHasTheBall != WhoHasTheBall.BACKGROUND) {
                    if (isThreadCancelled) {
                        // If the background thread has been cancelled then drop out of this loop and return false.
                        break;
                    }
                    try {
                        // Wait for the foreground thread to return control to the background.
                        syncObj.wait();
                    } catch (InterruptedException ex) {
                        // Ignore the InterruptedException and keep looping.
                    }
                }//while
                if (isThreadCancelled) {
                    throw new InterruptedException();
                }
            }//synchronized

            // Run the generator from within the background thread.
            generator();
            //TODO: consider providing a mechanism to pass any exception caught here
            //      up to the hasNext() method on the foreground thread.

        } catch (InterruptedException ex) {
            // Ignore the InterruptedException.
        } finally {
            synchronized (syncObj) {
                isThreadCancelled = true;
                whoHasTheBall = WhoHasTheBall.FOREGROUND;
                syncObj.notifyAll();
            }
        }
    }


    protected boolean canKeepGoing() {
        return !isThreadCancelled;
    }


    /**
     * The descendant implementation of this method is where all the work happens.
     * <br>
     * <b>Important! This method runs in a background thread.</b><br>
     * However, the purpose of this class is to provide a Thread Safe Environment for this code to run.
     * @throws InterruptedException
     */
    protected abstract void generator() throws InterruptedException;


    public void yieldReturn(T item) throws InterruptedException {
        // Enforce that this method is only called from the background 'thread'.
        if (Thread.currentThread() != thread) {
            String msg = "yieldReturn(...) must only be called from the background generator thread!";
            throw new InterruptedException(msg);
        }

        if (isThreadCancelled) {
            throw new InterruptedException();
        }

        synchronized (syncObj) {
            if (isThreadCancelled) {
                throw new InterruptedException();
            }

            //---------------------------------------------------------------------------------------
            // All of the surrounding code is just to set these values safely and at the right time.
            //---------------------------------------------------------------------------------------
            nextValue = item;
            whoHasTheBall = WhoHasTheBall.FOREGROUND;
            syncObj.notifyAll();

            //--------------------------------------------------------------------------------------------
            // Wait for the foreground thread to call 'hasNext()' before COMPUTING the next value.
            //--------------------------------------------------------------------------------------------
            while (whoHasTheBall != WhoHasTheBall.BACKGROUND) {
                if (isThreadCancelled) {
                    // If the background thread has been cancelled then drop out of this loop and return false.
                    break;
                }

                try {
                    // Block the background until the foreground thread calls 'hasNext()'.
                    syncObj.wait();
                } catch (InterruptedException ex) {
                    // Ignore the InterruptedException and keep looping.
                }
            }//while

            if (isThreadCancelled) {
                throw new InterruptedException();
            }
        }//synchronized
    }

}