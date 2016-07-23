package util;

import org.junit.Assert;
import org.junit.Test;

public class FileHelper {

    public String getAbsolutePath(final String source) {

        if (source.startsWith("~/")) {
            return applyHomePath(source);
        }

        return source;
    }

    @Test
    public void testGetAbsolutePath() {
        String actual = getAbsolutePath("./pom.xml");
        String expect = "./pom.xml";
        Assert.assertEquals("getSourceDir", actual, expect);

        String actual1 = getAbsolutePath("~/.bash_profile");
        String expect1 = System.getProperty("user.home") + "/.bash_profile";
        Assert.assertEquals("getSourceDir1", actual1, expect1);
    }

    private String applyHomePath(String dir) {
        return dir.replaceFirst("^\\~\\/", System.getProperty("user.home") + "/");
    }

    @Test
    public void testApplyHomePath() {
        String expect = System.getProperty("user.home") + "/";
        String actual = applyHomePath("~/");
        Assert.assertEquals("applyHomeDir", actual, expect);
    }

    private String applyWorkPath(String dir) {
        return dir.replaceFirst("^\\.\\/", System.getProperty("user.dir") + "/");
    }

    @Test
    public void testApplyWorkPath() {
        String expect = System.getProperty("user.dir") + "/";
        String actual = applyWorkPath("./");
        Assert.assertEquals("applyWorkDir", expect, actual);
    }
}
