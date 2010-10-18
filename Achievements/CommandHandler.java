import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CommandHandler
{
	private boolean empty;
	private ArrayList<String[]> commandList = new ArrayList<String[]>();

   static final Logger log = Logger.getLogger("Minecraft");

	CommandHandler(String commands)
	{
		this.empty = true;
		if (commands == null)
			return;

		String[] split = commands.split(";");
		for (String c: split)
		{
			String[] s = c.split(" ");
			if (s.length < 2) {
				log.log(Level.SEVERE, "Invalid command " + c);
				continue;
			}

			this.commandList.add(s);
			this.empty = false;
		}
	}
	
	public void run(Player player)
	{
		if (isEmpty())
			return;
	
		int item, amount;
		for (String[] s: commandList)
		{	
			if (s[0].equalsIgnoreCase("item"))
			{
				if (s.length < 3) {
					log.log(Level.SEVERE, "Bad command (not enough arguments) correct is: item itemname amount");
					continue;
				}
				item = etc.getDataSource().getItem(s[1]);
				if (item == 0) {
					log.info("Bad command (invalid item) correct is: item itemname amount");
					continue;
				}
				try {
					amount = Integer.parseInt(s[2]);
				}
				catch (NumberFormatException ex) {
					log.log(Level.SEVERE, "Bad command (amount is not a number) correct is: item itemname amount");
					continue;
				}
				player.giveItem(item, amount);
				continue;
			}

			if (s[0].equalsIgnoreCase("group"))
			{
				if (s.length < 2) {
					log.log(Level.SEVERE, "Bad command (not enough arguments) correct is: group groupname");
					continue;
				}
				player.addGroup(s[1]);
				if (!etc.getDataSource().doesPlayerExist(player.getName())) {
					etc.getDataSource().addPlayer(player);
			   } else {
					etc.getDataSource().modifyPlayer(player);
			   }
				continue;
			}

			log.log(Level.SEVERE, "Unknown command " + s[0]);
		}
	}

	public boolean isEmpty()
	{
		return this.empty;
	}

	public String toString()
	{
		if (isEmpty())
			return "";
	
		String ret = "";
		for (String[] s: commandList)
		{
			for (String c: s)
			{
				ret = ret + c + " ";
			}
			ret = ret + ";";
		}
		return ret;
	}
}