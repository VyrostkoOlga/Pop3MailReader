package internet.mail.receiver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner 
{
	private static final Pattern RE_HELP = Pattern.compile(".*?(-help|--help|-h|--h)");
	
	public static void main( String [] args ) throws Exception
	{
		try
		{
			Matcher m = RE_HELP.matcher( args.toString( ) );
			if ( m.matches( ) )
			{
				help( );
				return;
			}
			if ( args.length < 3 )
			{
				System.out.println( "Not enough arguments: need pop3server_address user_name user_password ");
				return;
			}
			
			String pop3server = args[0];
			String userName = args[1];
			String userPassword = args[2];
		
			int letterNumStart = 1;
			int letterNumEnd;
		
			if ( args.length > 3)
			{
				try
				{
					String[] interv = args[3].split(":");
					letterNumStart = Integer.parseInt( interv[0] );
					letterNumEnd = Integer.parseInt( interv[1] );
				}
				catch (Exception ex)
				{
					throw new Exception( "Wrong letter diap: need in form startLetterNum:endLetterNum" );
				}
			}
			else
			{
				letterNumEnd = -1;
			}
			
			Pop3Receiver receiver = new Pop3Receiver( pop3server, userName, userPassword, 
													  letterNumStart, letterNumEnd );
			
			List<String> letterList = receiver.receive( );
			System.out.println(String.format("Mail to %s", userName ) );
			
			for (String letter: letterList)
			{
				System.out.println(letter);
			}
		}
		catch (Exception ex)
		{
			System.out.println(String.format("Exception during main process: %s", ex ) );
		}
	}
	
	public static void help( )
	{
		System.out.println( "USAGE: java -jar Pop3MailReceiver pop3server_address \n "
							+ "user_name user_pass [letter_num_start:letter_num_end]");
	}

}
