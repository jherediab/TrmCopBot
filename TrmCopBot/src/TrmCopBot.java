//import org.glassfish.jersey.message.internal.TracingInfo.Message;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterfaceProxy;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;

public class TrmCopBot extends TelegramLongPollingBot{

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "TrmCopBot";
	}

	@Override
	public void onUpdateReceived(Update update)  {
		// TODO Auto-generated method stub
		
		SendMessage  message = new  SendMessage();
		
		/**
		 * Valid from and valid to TCRM date format
		 */
		 String _DATE_RESPONSE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
		
		/**
		 * TCRM query date format
		 */
		String _DATE_QUERY_FORMAT = "yyyy-MM-dd";
		
		/**
		 * TCRM query value format
		 */
		String _VALUE_QUERY_FORMAT = "#0.00";

		/**
		 * TCRM date to query
		 */
		String _DATE_TO_QUERY = "2014-08-13";
		
		/**
		 * Web Service end point
		 */
		String _WEB_SERVICE_URL = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL";
		
		
		System.out.println(update.getMessage().getText());
		
		String command=update.getMessage().getText();
		
		if (command.equals("/trm"))
		{
			//System.out.println("3985");
			
			
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_DATE_RESPONSE_FORMAT);
			
			//
			// Decimal value format declaration
			DecimalFormat decimalFormat = new DecimalFormat(_VALUE_QUERY_FORMAT);
			
			TCRMServicesInterfaceProxy proxy = new TCRMServicesInterfaceProxy(_WEB_SERVICE_URL);
					
			//
			// Gets the TCRM value for the current date
			TcrmResponse tcrmResponse;
			try {
				tcrmResponse = proxy.queryTCRM(null);
				
				
				System.out.println("Identificador: " + tcrmResponse.getId());
				System.out.println("TCRM Valida desde: " + simpleDateFormat.format(tcrmResponse.getValidityFrom().getTime()));
				System.out.println("TCRM Valida hasta: " + simpleDateFormat.format(tcrmResponse.getValidityTo().getTime()));
				System.out.println("Valor: " + decimalFormat.format(tcrmResponse.getValue()));
				
				message.setText("TRM Valida desde: " + simpleDateFormat.format(tcrmResponse.getValidityFrom().getTime())
						+"\nTRM Valida hasta: " + simpleDateFormat.format(tcrmResponse.getValidityTo().getTime())
						+"\nValor: "+decimalFormat.format(tcrmResponse.getValue()) );
				//message.setText("TCRM Valida hasta: " + simpleDateFormat.format(tcrmResponse.getValidityTo().getTime()));
				//message.setText("valor: "+decimalFormat.format(tcrmResponse.getValue()));
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		message.setChatId(update.getMessage().getChatId());
		
		
		try
		{
			execute(message);	
		}catch (TelegramApiException e){
			e.printStackTrace();
		}
		
		
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "1056358363:AAGeHgh2GonqYfTKxumVE_nSVuaGTiXjVQE";
	}

}
