package x;

public class XCmdToChangeScene extends XLoggableCmd {
    // fields
    private XScene mFromScene = null;
    private XScene mToScene = null;
    private XScene mReturnScene = null;
    
    // constructor
    private XCmdToChangeScene(XApp app, XScene toScene, XScene returnScene) {
         super(app);
         this.mFromScene = this.mApp.getScenarioMgr().getCurScene();
         this.mToScene = toScene;
         this.mReturnScene = returnScene;
    }
    
    // static methods to construct and execute this command
    public static boolean execute(XApp app, XScene toScene,
        XScene returnScene) {
        XCmdToChangeScene cmd =
            new XCmdToChangeScene(app, toScene, returnScene);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        this.mToScene.mReturnScene = this.mReturnScene;
        this.mApp.getScenarioMgr().setCurScene(this.mToScene);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mFromScene.getClass().getSimpleName()).append("\t");
        XScene curScene = this.mApp.getScenarioMgr().getCurScene();
        sb.append(curScene.getClass().getSimpleName()).append("\t");
        if(this.mReturnScene == null) {
            sb.append("null");
        } else {
            sb.append(curScene.getReturnScene().getClass().getSimpleName());
        }
        return sb.toString();
    }
}
