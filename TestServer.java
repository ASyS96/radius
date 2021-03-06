/**
 * A test radius server modified after org.tinyradius.test.TestServer.
 * All credit goes to the original creator, Matthias Wuttke.
 */
package org.tinyradius.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.tinyradius.packet.AccessRequest;
import org.tinyradius.packet.RadiusPacket;
import org.tinyradius.util.RadiusException;
import org.tinyradius.util.RadiusServer;

/**
 * Test server which terminates after 15 mins.
 * Knows only the client "localhost" with secret "testing123" and
 * the user "mw" with the password "test".
 */
public class TestServer {
    
    public static void main(String[] args) 
    throws IOException, Exception {
        RadiusServer server = new RadiusServer() {
            // Authorize localhost/testing123
            public String getSharedSecret(InetSocketAddress client) {
                if (client.getAddress().getHostAddress().equals("127.0.0.1"))
                    return "testing123";
                else
                    return null;
            }
            
            // Authenticate mw
            public String getUserPassword(String userName) {
                if (userName.equals("mw"))
                    return "test";
                else
                    return null;
            }
            
            // Adds an attribute to the Access-Accept packet
            public RadiusPacket accessRequestReceived(AccessRequest accessRequest, InetSocketAddress client) 
            throws RadiusException {
                System.out.println("Received Access-Request:\n" + accessRequest);
                RadiusPacket packet = super.accessRequestReceived(accessRequest, client);
                if (packet.getPacketType() == RadiusPacket.ACCESS_ACCEPT)
                    packet.addAttribute("Reply-Message", "Welcome " + accessRequest.getUserName() + "!");
                if (packet == null)
                    System.out.println("Ignore packet.");
                else
                    System.out.println("Answer:\n" + packet);
                return packet;
            }
        };
        if (args.length >= 1)
            server.setAuthPort(Integer.parseInt(args[0]));
        if (args.length >= 2)
            server.setAcctPort(Integer.parseInt(args[1]));
        
        server.start(true, true);
        
        System.out.println("Server started.");
        
        Thread.sleep(5000 * 12 * 15);//Server will die in 15 minutes
        System.out.println("Stop server");
        server.stop();
    }
    
}
