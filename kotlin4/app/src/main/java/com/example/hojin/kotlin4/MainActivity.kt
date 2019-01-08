package com.example.hojin.kotlin4

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64InputStream
import android.util.Log
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.setContentView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpCookie
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainUI = MainActivityUI()
        mainUI.setContentView(this)

    }

    fun QueryYahoo(ui: AnkoContext<MainActivity>, city: CharSequence?): String {
        Log.i("this is main",String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=c151c3e1a3f3a69e94d9f40d5a08a278", city))
        var urlTemplate = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=c151c3e1a3f3a69e94d9f40d5a08a278", city)



        var finalString = MyAsyncTask().execute(urlTemplate).get()

        return finalString[1]
    }
    class MyAsyncTask: AsyncTask<String?, Unit, List<String>>() {
        override protected fun doInBackground(vararg p0: String?): List<String> {

                val url = URL(p0[0])
            var ls1= listOf<String>("","","")
            Log.i("this is main", url.toString())
            try {
                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000
                var inString = ConvertStreamToString(urlConnect.inputStream)

                Log.i("success", inString)
                var json = JSONObject(inString)
                val sys1 = json.getJSONObject("sys")
                val sunrise = sys1.getString("sunrise")
                val coord = json.getJSONObject("coord")
                val mainData = json.getJSONObject("main")
                val temperature = mainData.getString("temp")
                val lat = coord.getString("lat")
                ls1 = listOf<String>(sunrise, temperature, lat)
                Log.i("success", sunrise)
            }
            catch (ex:Exception){
                ls1= listOf<String>("incorrect","incorrect","incorrect")
            }
            return ls1

        }



        fun ConvertStreamToString(inputStream: InputStream):String {
            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line :String
            var AllString: String=""
            try {
                do{
                    line = bufferReader.readLine()
                    if (line!=null){AllString+=line
                    }

                }while(line!=null)
                inputStream.close()
            }catch (ex:Exception){}
            return  AllString
        }
    }
}