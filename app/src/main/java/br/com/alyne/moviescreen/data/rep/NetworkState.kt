package br.com.alyne.moviescreen.data.rep

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Loading")
            ERROR = NetworkState(Status.FAILED,"Something went wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end of the list")
        }
    }
}