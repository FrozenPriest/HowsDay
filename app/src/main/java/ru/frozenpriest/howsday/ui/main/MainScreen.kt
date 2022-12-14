@file:OptIn(ExperimentalPermissionsApi::class)

package ru.frozenpriest.howsday.ui.main

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.tasks.TaskExecutors
import com.google.common.util.concurrent.ListenableFuture
import org.koin.androidx.compose.koinViewModel
import ru.frozenpriest.howsday.R

@Composable
fun MainScreen() {

    val viewModel: MainViewModel = koinViewModel()

    val state by viewModel.stateFlow.collectAsState()

    Surface(color = MaterialTheme.colors.background) {

        var lens by remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }

        val imageCapture by remember {
            mutableStateOf(ImageCapture.Builder().build())
        }

        val cameraPermissionState = rememberPermissionState(
            android.Manifest.permission.CAMERA
        )

        viewModel.permissionGranted(cameraPermissionState.status.isGranted)

        if (state != MainState.NoPermission) {
            CameraPreview(
                cameraLens = lens,
                imageCapture = imageCapture,
            )
            Controls(
                onLensChange = { lens = switchLens(lens) },
                onCaptureImage = {
                    imageCapture.takePicture(
                        TaskExecutors.MAIN_THREAD,
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                super.onCaptureSuccess(image)

                                viewModel.imageTaken(image)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                println(exception)
                            }
                        }
                    )
                }
            )

            if (state is MainState.Result) {
                ResultCard(state)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    "No image recognition without camera for you. Please grant the permission."
                } else {
                    "Camera permission required for image recognition. " +
                        "Please grant the permission"
                }

                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = textToShow
                )

                Button(
                    modifier = Modifier.align(CenterHorizontally),
                    onClick = { cameraPermissionState.launchPermissionRequest() }
                ) {
                    Text("Request permission")
                }
            }
        }
    }
}

@Composable
private fun ResultCard(state: MainState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(
                    id = R.string.result,
                    (state as MainState.Result).classificationResult.result.name
                ),
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Composable
fun Controls(
    onLensChange: () -> Unit,
    onCaptureImage: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onLensChange,
                modifier = Modifier.wrapContentSize()
            ) { Icon(Icons.Filled.Cameraswitch, contentDescription = "Switch camera") }

            Button(
                onClick = { onCaptureImage() },
                modifier = Modifier.wrapContentSize()
            ) { Icon(Icons.Filled.PhotoCamera, contentDescription = "Switch camera") }
        }
    }
}

private fun switchLens(lens: Int) = if (CameraSelector.LENS_FACING_FRONT == lens) {
    CameraSelector.LENS_FACING_BACK
} else {
    CameraSelector.LENS_FACING_FRONT
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraLens: Int,
    imageCapture: ImageCapture,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val previewView = remember { PreviewView(context) }

    val cameraProvider = remember(cameraLens) {
        ProcessCameraProvider.getInstance(context)
            .configureCamera(previewView, lifecycleOwner, imageCapture, cameraLens, context)
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CameraPreview(previewView)
    }
}

@Composable
private fun CameraPreview(previewView: PreviewView) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            previewView.apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Preview is incorrectly scaled in Compose on some devices without this
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            previewView
        }
    )
}

private fun ListenableFuture<ProcessCameraProvider>.configureCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture,
    cameraLens: Int,
    context: Context,
): ListenableFuture<ProcessCameraProvider> {
    addListener({
        val cameraSelector = CameraSelector.Builder().requireLensFacing(cameraLens).build()

        val preview = androidx.camera.core.Preview.Builder()
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        try {
            get().apply {
                unbindAll()
                bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture, preview)
            }
        } catch (exc: Exception) {
            TODO("process errors")
        }
    }, ContextCompat.getMainExecutor(context))
    return this
}
