package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

public class TrackDAO implements ITrackDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public List<Track> getAllAvailableTracks(int id) {

        String sql = "";

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
