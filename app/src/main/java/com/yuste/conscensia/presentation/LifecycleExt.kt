package com.yuste.conscensia.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Only call after view was created and before it was destroyed.
 * Ensures a [block] is executed within valid not destroyed fragment
 * */
fun Fragment.launchCoroutineForUiState(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    require(view != null) {
        "Can't access launch ui state flow collection when" +
                "getView() is null i.e., before onCreateView() or after onDestroyView()"
    }

    return viewLifecycleOwner.lifecycleScope.launch {
        // Guard against cases when this @launch bloc has started after view was destroyed
        if (view != null) viewLifecycleOwner.repeatOnLifecycle(state, block)
    }
}