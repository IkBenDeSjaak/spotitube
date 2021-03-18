package han.oose.dea.services;

import han.oose.dea.dao.ITrackDAO;
import han.oose.dea.domain.Track;

import javax.inject.Inject;
import java.util.List;

public class TracksService {

    private ITrackDAO trackDAO;

    public List<Track> getAllAvailableTracksForPlaylist(int playlistId) {
        return trackDAO.getAllAvailableTracksForPlaylist(playlistId);
    }

    public List<Track> getAllTracksFromPlaylist(int playlistId) {
        return trackDAO.getAllTracksFromPlaylist(playlistId);
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
