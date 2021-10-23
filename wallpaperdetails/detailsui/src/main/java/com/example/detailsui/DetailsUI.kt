package com.example.detailsui

import android.Manifest
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.crazylegend.toaster.Toaster
import com.example.detailsdata.DetailsDataState
import com.example.detailsdata.DetailsViewModel
import com.example.network.response.CommonPic
import com.example.toaster.ToasterViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailUI(){

    val viewModel: DetailsViewModel = hiltViewModel()
    val toaster: ToasterViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val stateFlowLifecycleAware = remember(viewModel.state, lifecycleOwner){
        viewModel.state.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val state by stateFlowLifecycleAware.collectAsState(initial = DetailsDataState())
    var loadImage by remember{ mutableStateOf(true)}
    val permissionState = rememberPermissionState(permission = Manifest.permission.SET_WALLPAPER)
    var doNotShowMeRationale by rememberSaveable {
        mutableStateOf(false)
    }
    var setWallpaper by remember{ mutableStateOf(false)}
    var onLoadBitmap by remember { mutableStateOf(false)}

/*
        RequirePermission(
            permissionState = permissionState,
            toaster = toaster,
            doNotShowMeRationale = doNotShowMeRationale,
            onResetPermission = {setWallpaper = false}
        )*/



    if(setWallpaper){
        state.commonPic?.url?.let {
            SetAsWallpaper(
                context = context,
                uri = it,
                toaster,
                onloadingImage = {
                    onLoadBitmap = it
                }
            )
            setWallpaper = false
        }
    }

   Scaffold(
       topBar = {
           TopAppBar(
               title={
                   state.commonPic?.tags?.let {
                       Text(
                           text = it
                       )
                   }
               },
               navigationIcon = {
                    IconButton(onClick = { viewModel.navigateUp() }) {
                       Icon(
                           imageVector = Icons.Default.ArrowBack,
                           contentDescription = null
                       )
                    }
               },
               modifier = Modifier.statusBarsPadding(),
               elevation = 0.dp
           )
       },
       content = {
           Box(
               modifier = Modifier.fillMaxSize()
           ){
               if(onLoadBitmap){
                   CircularProgressIndicator(modifier = Modifier.align(Alignment.TopCenter))
               }
               if(state.isLoading || loadImage){
                   CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
               }
               state.commonPic?.let { commonPic -> ImageContent(
                   commonPic = commonPic
               ) }
               FloatingBar(
                   modifier = Modifier
                       .align(Alignment.BottomCenter)
                       .padding(bottom = 92.dp),
                   onClick = {
                       if(!permissionState.hasPermission){
                           Log.i("PERMISSION", "No permission granted")
                       }else{
                            setWallpaper = true
                       }
                   }
               )
           }
       },
       bottomBar = {
            BottomBar(modifier = Modifier.navigationBarsPadding())
       }
   )
}



@Composable
fun FloatingBar(
    modifier: Modifier,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
    ) {
        FloatingActionButton(
            onClick = onClick
        ) {
            Icon(imageVector = Icons.Filled.Save, contentDescription = null)
        }
    }
}


@Composable
fun ImageContent(
    commonPic: CommonPic
){

    val painter = rememberImagePainter(
        data = commonPic.fullHDURL
    )

    when(painter.state){
        is ImagePainter.State.Loading ->{
            Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
            }
        }
        is ImagePainter.State.Success,  ImagePainter.State.Empty , is ImagePainter.State.Error->{
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(commonPic.height.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
){
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            FavoriteButton(onClick = {})
            BookmarkButton(isBookmarked = false, onClick = { /*TODO*/ })
            ShareButton(onClick = {})
        }
    }
}


@Composable
fun FavoriteButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.ThumbUpOffAlt,
            contentDescription = null
        )
    }
}



@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.high
) {
    val clickLabel = stringResource(
        if (isBookmarked) com.example.strings.R.string.unbookedmarked else com.example.strings.R.string.bookmark
    )
    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        IconToggleButton(
            checked = isBookmarked,
            onCheckedChange = { onClick() },
            modifier = modifier.semantics {
                this.onClick(label = clickLabel, action = null)
            }
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                contentDescription = null // handled by click label of parent
            )
        }
    }
}


@Composable
fun ShareButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = stringResource(com.example.strings.R.string.share)
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequirePermission(
    permissionState: PermissionState,
    toaster: ToasterViewModel,
    doNotShowMeRationale: Boolean,
    onResetPermission: () -> Unit
){

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            if(doNotShowMeRationale){
                toaster.longToast("permission is denied, open settings to grant permission")
            }else{
                permissionState.launchPermissionRequest()

            }
        },
        permissionNotAvailableContent = {
            toaster.longToast("Opening settings.......")
            onResetPermission()
        },
        content = {
            toaster.longToast("Permission is granted, you can go ahead")

        }
    )
}

@Composable
private fun SetAsWallpaper(
    context: Context,
    uri: String,
    toaster: ToasterViewModel,
    onloadingImage: (loading: Boolean) ->  Unit
){
    val scope = rememberCoroutineScope()
    val manager = WallpaperManager.getInstance(context)



     LaunchedEffect(true){
         scope.launch {
             try {
                 onloadingImage(true)
                 val imageUri = getBitmap(uri = uri, context = context)
                 imageUri?.let {
                     val path = getImageUri(it, context)
                     context.startActivity(Intent(manager.getCropAndSetWallpaperIntent(path)))
                     onloadingImage(false)
                 }
                 //context.startActivity(Intent(manager.getCropAndSetWallpaperIntent()))
             }catch (e: Exception){
                toaster.shortToast("Opps! something Went wrong, please try again")
                 onloadingImage(false)
             }
         }
     }

}




private fun  getBitmap(
    uri: String,
    context: Context
): Bitmap?{
    var bitmap: Bitmap? = null
    try{
        bitmap = Glide.with(context)
            .asBitmap()
            .load(uri)
            .submit()
            .get()
    }catch (e: IOException){

    }
    return bitmap
}



private fun getImageUri(bitmap: Bitmap, context: Context): Uri{
    val byte = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byte)
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver, bitmap, "Title",null
    )
    return Uri.parse(path)
}



