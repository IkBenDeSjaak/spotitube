package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import java.util.List;

public interface ITrackDAO {

    List<Track> getAllAvailableTracks(int playlistId);

    List<Track> getAllTracksFromPlaylist(int playlistId);

    void deleteTrackFromPlaylist(int playlistId, int trackId);

    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);

    void deleteAllTracksFromPlaylist(int playlistId);
}
