package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PropUtil;

import java.util.Properties;

public class ConfigHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

    public static final String PACKAGE ;
    public static final String VIEWPATH;
    public static final String ASSETPATH;

    static{
        Properties prop = PropUtil.loadProperties("springtest.properties");
        PACKAGE=PropUtil.getString(prop,"springtest.base.package");
        VIEWPATH=PropUtil.getString(prop,"springtest.base.view");
        ASSETPATH=PropUtil.getString(prop,"springtest.base.asset");
    }

}
