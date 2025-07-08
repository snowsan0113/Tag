package snowsan0113.tag.manager.mission;

public interface Mission {

    void stopMission(MissionManager.MissionResult result, String message);
    String getMessage();
    MissionManager.GameMissionType getType();
}
