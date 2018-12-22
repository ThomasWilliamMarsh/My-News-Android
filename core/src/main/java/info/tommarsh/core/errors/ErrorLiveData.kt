package info.tommarsh.core.errors

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import info.tommarsh.core.network.NetworkException
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class ErrorLiveData @Inject constructor() : LiveData<NetworkException>() {

    private val pending = AtomicBoolean(false)

    fun setError(value: NetworkException) {
        pending.set(true)
        super.postValue(value)
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in NetworkException>) =
        super.observe(owner, Observer<NetworkException> { value ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        })
}