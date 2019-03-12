package org.nico.yasso.utils.test;

import org.junit.Test;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;

public class CommandUtilsTest {

    @Test
    public void executeTest() {

        String[] ss = new String[] {"git clone https://ainilili:Lqy199685@github.com/ainilili/trap.git",
                "cd trap",
        "git pull"};

        Result result = CommandUtils.execute("git clone https://ainilili:Lqy199685@github.com/ainilili/trap.git", "D:\\workspace-yasso");
        System.out.println(result);
        result = CommandUtils.execute("cmd.exe cd trap", "D:\\workspace-yasso");
        System.out.println(result);
        result = CommandUtils.execute("git pull", "D:\\workspace-yasso");
        System.out.println(result);

        
    }

}
