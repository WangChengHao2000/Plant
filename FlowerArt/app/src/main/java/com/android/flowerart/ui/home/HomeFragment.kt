package com.android.flowerart.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.flowerart.FlowerApplication
import com.android.flowerart.R
import com.android.flowerart.logic.bean.Flower
import com.android.flowerart.logic.bean.Results
import com.android.flowerart.ui.plantInfo.PlantInfoActivity
import com.android.flowerart.utils.*
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }

    private val takePhoto = 1
    private val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val resultsLayoutManager = GridLayoutManager(activity, 1)
        val resultsRecyclerView = activity?.findViewById<RecyclerView>(R.id.recognizeResults)
        resultsRecyclerView?.layoutManager = resultsLayoutManager
        resultAdapter = ResultAdapter(requireActivity(), viewModel.resultList)
        resultsRecyclerView?.adapter = resultAdapter

        activity?.findViewById<Button>(R.id.takePhotos)?.setOnClickListener {
            outputImage = File(activity?.externalCacheDir, "output_image.jpg")
            if (outputImage.exists())
                outputImage.delete()
            outputImage.createNewFile()
            imageUri = FileProvider.getUriForFile(
                FlowerApplication.context,
                "com.android.flowerart.fileprovider",
                outputImage
            )

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        activity?.findViewById<Button>(R.id.fromAlbums)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }

        activity?.findViewById<Button>(R.id.recognizePhotos)?.setOnClickListener {
            val file = File(activity?.externalCacheDir, "result.jpg")
            if (file.exists()) {
                val filePath = activity?.externalCacheDir.toString() + "/result.jpg"
                viewModel.recognize(filePath)
            } else {
                Toast.makeText(activity, "请先选择照片", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.recognizeResultLiveData.observe(viewLifecycleOwner, { result ->
            if (result != null) {
                viewModel.resultList.clear()
                viewModel.resultList.addAll(result)
                resultAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "查询无结果", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        activity?.contentResolver?.openInputStream(imageUri)
                    )
                    val rotatedBitmap = ImageUtil.rotateIfRequired(outputImage, bitmap)
                    activity?.findViewById<ImageView>(R.id.photoView)
                        ?.setImageBitmap(rotatedBitmap)

                    try {
                        val file = File(activity?.externalCacheDir, "result.jpg")
                        val out = FileOutputStream(file)
                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, out)
                        out.flush()
                        out.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = activity?.contentResolver?.openFileDescriptor(uri, "r")?.use {
                            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                        }
                        activity?.findViewById<ImageView>(R.id.photoView)?.setImageBitmap(bitmap)

                        val file = File(activity?.externalCacheDir, "result.jpg")
                        val out = FileOutputStream(file)
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 30, out)
                        out.flush()
                        out.close()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val file = File(activity?.externalCacheDir, "result.jpg")
        if (file.exists())
            file.delete()
    }

    inner class ResultAdapter(private val context: Context, private val resultList: List<Flower>) :
        RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val resultName: TextView = view.findViewById(R.id.resultName)
            val resultScore: TextView = view.findViewById(R.id.resultScore)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ResultAdapter.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.results_item, parent, false)
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val name = resultList[holder.adapterPosition].name
                val plantID = APIShopUtil.getPlantID(name)
                val intent = Intent(activity, PlantInfoActivity::class.java)
                intent.putExtra("plantID", plantID)
                activity?.startActivityForResult(intent, 0)
            }
            return holder
        }

        override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) {
            val result = resultList[position]
            holder.resultName.text = result.name
            holder.resultScore.text = result.score.toString()
        }

        override fun getItemCount(): Int {
            return resultList.count()
        }
    }

}