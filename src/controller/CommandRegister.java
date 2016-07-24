package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import command.Command;
import command.InvalidCommand;

/**
 * script command 에 해당하는 command package 의 class 를 보관한다.
 * 
 * @author johngrib
 */
public class CommandRegister {

    private HashMap<String, Command> commands;

    public CommandRegister() {
        super();
        commands = new HashMap<>();
    }

    public Command getCommand(String function) {
        if (commands.containsKey(function)) {
            return commands.get(function);
        }

        try {

            final String class_name = "command." + function;

            @SuppressWarnings("unchecked")
            Class<Command> cmd_class = (Class<Command>) Class.forName(class_name);
            Constructor<Command> constructor = cmd_class.getConstructor();

            Command cmd = constructor.newInstance(new Object[] {});
            commands.put(function, cmd);

            return cmd;

        } catch (ClassNotFoundException e) {
            Command cmd = new InvalidCommand();
            commands.put(function, cmd);
            return cmd;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    };
}
