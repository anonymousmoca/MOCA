package MOCO;

import IoTSystem.Message;
import IoTSystem.TaskScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

public class MessageProxy {
    private static final Logger LOGGER = LogManager.getLogger();

    private TaskScheduler taskScheduler;
    private Map<String, Set<String>> gateway_outEdgeApis;
    private Map<String, Set<String>> light_outEdgeApis;
    private Map<String, Set<String>> cm_outEdgeApis;
    private Map<String, Set<String>> vc_outEdgeApis;
    private Map<String, Set<String>> wm_outEdgeApis;
    private boolean proxyOn;

    public MessageProxy(TaskScheduler taskScheduler, Map<String, Set<String>> gateway_outEdgeApis, Map<String, Set<String>> light_outEdgeApis, Map<String, Set<String>> cm_outEdgeApis,
                        Map<String, Set<String>> vc_outEdgeApis, Map<String, Set<String>> wm_outEdgeApis, boolean proxyOn) {
        this.taskScheduler = taskScheduler;
        this.gateway_outEdgeApis = gateway_outEdgeApis;
        this.light_outEdgeApis = light_outEdgeApis;
        this.cm_outEdgeApis = cm_outEdgeApis;
        this.vc_outEdgeApis = vc_outEdgeApis;
        this.wm_outEdgeApis = wm_outEdgeApis;
        this.proxyOn = proxyOn;
    }

    public void setProxyOn(boolean proxyOn) {
        this.proxyOn = proxyOn;
    }

    public boolean addMessage (Message message, String currentState) {
        if (proxyOn) {
            if (ExecutionChecker.preCheck(message, currentState, gateway_outEdgeApis, light_outEdgeApis, cm_outEdgeApis, vc_outEdgeApis, wm_outEdgeApis)){
                taskScheduler.addMessage(message);
                return true;
            }else{
                LOGGER.info("Current Msg can not be executed at the current state. " + message);
                LOGGER.info("Current state: " + currentState);
                return false;
            }
        }else {
            taskScheduler.addMessage(message);
            return true;
        }
    }
}
