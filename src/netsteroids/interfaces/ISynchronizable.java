package netsteroids.interfaces;

import netsteroids.network.Packet;

public interface ISynchronizable {
    Packet OnSyncRequest();
    void OnSyncResponse(Packet response);
}
