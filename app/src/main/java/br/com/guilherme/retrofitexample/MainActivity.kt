package br.com.guilherme.retrofitexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = configuraRetrofit()

        executaRequisicao(retrofit)
    }

    private fun executaRequisicao(retrofit: Retrofit) {

        val githubService = retrofit.create(GitHubService::class.java)

        githubService.getRepositories("guilhermeftsa").enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {
                Log.d("tag", "falhou master!")
            }

            override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
                Log.d("tag", "deu certo")

                response?.let {

                    if (it.isSuccessful){
                        response.body()?.let{

                            it.forEach { repository ->
                                Log.d("tag", "Repositório: ${repository.name} foi escrito em ${repository.language}")
                            }

                        }

                    }else{
                        Log.d("tag", "falhou menos")
                    }

                }
            }

        })
    }

    private fun configuraRetrofit():Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
