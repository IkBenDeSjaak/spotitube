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

    public TracksDTO getAllAvailableTracks(int playlistId) {
        List<Track> tracks = trackDAO.getAllAvailableTracks(playlistId);
        TracksDTO tracksDTO = convertTracksListToTracksDTO(tracks);
        return tracksDTO;
    }

    private TracksDTO convertTracksListToTracksDTO(List<Track> tracks) {
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for(Track track : tracks) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = track.getId();
            trackDTO.title = track.getTitle();
            trackDTO.performer = track.getPerformer();
            trackDTO.duration = track.getDuration();
            trackDTO.album = track.getAlbum();
            trackDTO.playcount = track.getPlaycount();
            trackDTO.publicationDate = track.getPublicationDate();
            trackDTO.description = track.getDescription();
            trackDTO.offlineAvailable = track.isOfflineAvailable();

            tracksDTO.tracks.add(trackDTO);
        }

        return tracksDTO;
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
