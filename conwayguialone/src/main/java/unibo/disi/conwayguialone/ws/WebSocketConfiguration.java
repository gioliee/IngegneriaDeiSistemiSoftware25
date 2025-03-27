package unibo.disi.conwayguialone.ws;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import unibo.basicomm23.utils.CommUtils;

 
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	public final String wsPath  = "wsupdates";
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		CommUtils.outblue( "WebSocketConfiguration | registerWebSocketHandlers" );
 		
		//CASO Life esterno con uso di MQTT
		//WSConwayguiLifeMqttBase wsgui =  WSConwayguiLifeMqttBase.getInstance();
		//WSConwayguiLifeMqttBaseCB wsgui =  WSConwayguiLifeMqttBaseCB.getInstance();
		WSConwayguiLifeMqtt wsgui =  WSConwayguiLifeMqtt.getInstance();
		registry.addHandler(wsgui, wsPath).setAllowedOrigins("*");
 	}
}