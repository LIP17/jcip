package chapter7;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Listing 7.14 Encapsulating nonstandard cancellation in a Thread by overriding interrupt.
 */
public class InterruptNonInterruptable {

    private class ReaderThread extends Thread {
        private final Socket socket;
        private final InputStream in;

        public ReaderThread(Socket socket) throws IOException {
            this.socket = socket;
            this.in = socket.getInputStream();
        }

        /**
         * Some method or mechanism is not interruptible, like InputStream
         * and OutputStream. So in this case, when we interrupt, we can do it
         * over the underlying socket, and let the catch the exception to exit the
         * input stream.
         * */

        @Override
        public void interrupt() {
            try {
                socket.close();
            } catch (IOException ignored) {
                /**
                 * when you read the input stream underlying later, you will
                 * get IO exception
                 * */
            } finally {
                super.interrupt();
            }
        }

        public void run() {
            try {
                byte[] buf = new byte[100];
                while (true) {
                    int count = in.read(buf);
                    if(count < 0) break;
                    else doSomethingOnData(buf, count);
                }
            } catch (IOException ie) {

                /**
                 * The socket is closed by interrupt(), so here
                 * you got IO exception which means you have to exit the thread here
                 * */

            }
        }

        private void doSomethingOnData(byte[] buf, int count) {

        }
    }
}
