package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import java.util.List;

public interface ITrackDAO {

    List<Track> getAllAvailableTracks(int id);
}
