package cn.zplayer.mc.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import cn.zplayer.mc.R
import kotlinx.android.synthetic.main.activity_kot_lin_test1.*


class KotLinTest1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kot_lin_test1)
        tv_show_message.setTextColor(resources.getColor(R.color.colorAccent))
        tv_show_message.text= Editable.Factory().newEditable("hello KitLin")
        tv_show_message.text=sunString();
    }

    fun sunString():String{
        return test1Result1()+"\n"+test2Result2(3,6)+"\n"+test2Result3(arrayListOf("a","b","c"))
    }

    fun test1Result1():String{
        var s1="xx1"
        var s2="xx2"
        return s1+s2
    }

    fun test2Result2(a: Int,b: Int)=if(a>b) (a+b) else (a-b)

    fun test2Result3(args: List<String>): String{
        if(args.size>0){
            var str=args
            return "args:${str}"
        }else{
            return "空";
        }
    }
}
