package org.trolo.simple;

/**
 * @author Stanislav Kurilin
 */
enum Event {
    STARTED,    //The first request to the tracker must include the event key with this value.
    STOPPED,    //Must be sent to the tracker if the client is shutting down gracefully.
    COMPLETED   //Must be sent to the tracker when the download completes.
}
