package fr.litarvan.openauth.microsoft;

import javax.swing.*;
import java.util.concurrent.*;
import javafx.embed.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.application.*;
import sun.net.www.protocol.https.*;
import java.io.*;
import javafx.scene.web.*;
import javafx.scene.*;
import javafx.beans.value.*;
import java.net.*;

public class LoginFrame extends JFrame
{
    private CompletableFuture<String> future;
    
    public LoginFrame() {
        this.setTitle("Connexion \u00e0 Microsoft");
        this.setSize(750, 750);
        this.setLocationRelativeTo(null);
        this.setContentPane(new JFXPanel());
    }
    
    public CompletableFuture<String> start(final String url) {
        if (this.future != null) {
            return this.future;
        }
        this.future = new CompletableFuture<String>();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                LoginFrame.this.future.completeExceptionally(new MicrosoftAuthenticationException("User closed the authentication window"));
            }
        });
        Platform.runLater(() -> this.init(url));
        return this.future;
    }
    
    protected void init(final String url) {
        URL.setURLStreamHandlerFactory(protocol -> {
            if ("https".equals(protocol)) {
                return new Handler() {
                    @Override
                    protected URLConnection openConnection(final URL url) throws IOException {
                        return this.openConnection(url, null);
                    }
                    
                    @Override
                    protected URLConnection openConnection(final URL url, final Proxy proxy) throws IOException {
                        final HttpURLConnection connection = (HttpURLConnection)super.openConnection(url, proxy);
                        if (("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/oauth2/authorize")) || ("login.live.com".equals(url.getHost()) && "/oauth20_authorize.srf".equals(url.getPath())) || ("login.live.com".equals(url.getHost()) && "/ppsecure/post.srf".equals(url.getPath())) || ("login.microsoftonline.com".equals(url.getHost()) && "/login.srf".equals(url.getPath())) || ("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/login")) || ("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/SAS/ProcessAuth")) || ("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/federation/oauth2")) || ("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/oauth2/v2.0/authorize"))) {
                            return new MicrosoftPatchedHttpURLConnection(url, connection);
                        }
                        return connection;
                    }
                };
            }
            else {
                return null;
            }
        });
        final WebView webView = new WebView();
        final JFXPanel content = (JFXPanel)this.getContentPane();
        content.setScene(new Scene(webView, this.getWidth(), this.getHeight()));
        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("access_token")) {
                this.setVisible(false);
                this.future.complete(newValue);
            }
            return;
        });
        webView.getEngine().load(url);
        this.setVisible(true);
    }
}
