package han.oose.dea.service;

import han.oose.dea.controller.dto.TracksDTO;
import han.oose.dea.dao.ITrackDAO;
import han.oose.dea.domain.Track;

import javax.inject.Inject;
import java.util.List;

public class TracksService {

    private ITrackDAO trackDAO;

    public TracksDTO getAllAvailableTracks(int id) {
        List<Track> tracks = trackDAO.getAllAvailableTracks(id);
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
