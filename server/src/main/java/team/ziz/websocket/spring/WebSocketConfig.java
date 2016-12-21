package team.ziz.websocket.spring;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
   
   @Override
   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
       registry.addHandler(myHandler(), "/myHandler")
         .addInterceptors(new HttpSessionHandshakeInterceptor());
   }
    
   @Bean
   public WebSocketHandler myHandler() {
      return new MyHandler();
   }
   
   @Bean
   public DefaultHandshakeHandler handshakeHandler() {

       WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
       policy.setInputBufferSize(8192);
       policy.setIdleTimeout(600000);

       return new DefaultHandshakeHandler(
             //TODO: JettyRequestUpgradeStrategy Constructor  Context null?
               new JettyRequestUpgradeStrategy(new WebSocketServerFactory(null,policy)));
   }

}
