package ru.frozenpriest.howsday.ui.camera

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
            .configureCamera(previewView, lifecycleOwner, cameraSelector, context)
    }

    AndroidView(
        modifier = modifier,
        factory = {
            previewView.apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
            previewView
        }
    )
}

private fun ListenableFuture<ProcessCameraProvider>.configureCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    context: Context
): ListenableFuture<ProcessCameraProvider> {
    addListener({
        val preview = androidx.camera.core.Preview.Builder()
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
        try {
            get().apply {
                unbindAll()
                bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview
                )
            }
        } catch (exc: Exception) {
            TODO("process errors")
        }
    }, ContextCompat.getMainExecutor(context))
    return this
}
