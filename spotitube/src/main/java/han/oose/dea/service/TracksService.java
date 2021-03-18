package han.oose.dea.service;

import han.oose.dea.controller.dto.TrackDTO;
import han.oose.dea.controller.dto.TracksDTO;
import han.oose.dea.dao.ITrackDAO;
import han.oose.dea.domain.Track;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TracksService {

    private ITrackDAO trackDAO;

    public List<Track> getAllAvailableTracksForPlaylist(int playlistId) {
        List<Track> tracks = trackDAO.getAllAvailableTracksForPlaylist(playlistId);
        return tracks;
    }

    public List<Track> getAllTracksFromPlaylist(int playlistId) {
        List<Track> tracks = trackDAO.getAllTracksFromPlaylist(playlistId);
        return tracks;
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        trackDAO.deleteTrackFromPlaylist(playlistId, trackId);
    }

    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        trackDAO.addTrackToPlaylist(playlistId, trackId, offlineAvailable);
    }

    public void deleteAllTracksFromPlaylist(int playlistId) {
        trackDAO.deleteAllTracksFromPlaylist(playlistId);
    }


    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
