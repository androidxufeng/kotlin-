package com.xufeng.mvpkotlin.app

import android.os.Handler
import android.os.Message
import android.util.Log


/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 * Kotlin 语法测试
 */
class test {

    init {
//        // 声明不允许为空 not null
//        var value1: String = "dff"
//
//        val length = value1.length
//
//        val int1: Int = value1.length
//
//        value1 = null  // 编译错误
//
//
//        // 声明可以为空
//        var value2: String? = "adb"
//        val length2 = value2.length //编译错误，编译器不知道你的变量是否是空
//        /*   等价于
//           if(value2 == null)
//           length3 = null;          // 这里result为一个引用类型
//           else
//           length3 = value2.length;*/
//        val length3 = value2?.length
//
//        /**
//         * var length4 : Int = if(value2 != null) value2.length else -1
//         */
//        val length4 = value2?.length ?: -1
//
//        val length5 = value2!!.length
//        value2 = null

        val result1 = resultOpt(2, 3, { a, b -> a * b })

        val result2 = resultOpt(3, 4) { s, b ->
            s - b
        }
        Log.d("ceshi", "1 == $result1  2== $result2")

        testRun()
        testWith()
        testApply()
        testAlso()
        testLet()
        testTakeif()
        testRepeat()
    }

    fun testObject() {
        //1.构建匿名内部类
        var handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                var a: Int = SingleInstance.test()
            }
        }
    }

    //2.直接修饰对象　　实现了单例
    // 内部不允许声明构造方法

    object SingleInstance {

        fun test(): Int {
            TODO()
            return 1
        }
    }

    //3.高阶函数
    fun resultOpt(num1: Int, num2: Int, opt: (Int, Int) -> Int): Int {
        return opt(num1, num2)
    }

    //4.标准高阶函数 内部有调用者的对象，返回值是内部函数的返回值，内部返回值默认是最后一句
    fun testRun() {
        var index = 3

        val tr = run {
            when (index) {
                0 -> "java"
                1 -> "php"
                2 -> "python"
                3 -> "javaScript"
                else -> "none"
            }
        }
        Log.d("ceshi", "tr = $tr")
        var str: String = "kotlin".run {
            Log.d("ceshi", "r1 = $length")
            Log.d("ceshi", "r2 = ${this.first()}")
            Log.d("ceshi", "r3 = ${last()}")
            "hah"
        }
        Log.d("ceshi", "str = $str")
    }

    //4.with
    fun testWith() {
        val num = 9
        val with = with(num) {
            val plus = this.plus(9)
            Log.d("ceshi", "plus = $plus")
            or(1)
        }
        Log.d("ceshi", "with = $with")

    }

    //5.apply 和with 的区别是ａｐｐｌｙ的返回值时调用者本身,并且内部调用的函数无返回值
    fun testApply() {
        var str = StringBuilder("xufeng")
        val apply = str.apply {
            val replace = str.append("111")
            Log.d("ceshi", "with = $replace")

        }
        Log.d("ceshi", "with = $apply")
    }

    //6.also 和ａｐｐｌｙ类似，区别是　内部函数有参数，调用时需要使用ｉｔ而非ｔｈｉｓ
    fun testAlso() {
        val also = "kotlin".also {
            println1("结果：${it.plus("-java")}")
        }.also {
            println1("结果：${it.plus("-php")}")
        }
        println(also)
        val apply = "kotlin".apply {
            println1("结果：${this.plus("-java")}")
        }.apply {
            println1("结果：${this.plus("-php")}")
        }
    }

    //7.let 和also类似，只不过返回值不再是调用者本身，而是内部函数的返回值

    fun  testLet(){
        var str = "kotlin"
        str.let {
            it.replace('x','X')
        }.let {
            println1("111 $it")
            it.reversed()
        }.let {
            println1("222 $it")
            it.endsWith("s")
        }.let {
            println1("$it")
        }
    }

    //8.takeIf ,判断某个对象是否满足某种条件，ｙｅｓ则返回本身，否则返回ｎｕｌｌ
    fun  testTakeif(){
        val str = "Kotlin"
        val takeIf1 = str.takeIf {
            str.startsWith('k')
        }

        val takeIf2 = str.takeIf {
            str.startsWith('K')
        }
        println1(takeIf1?:"sb")
        println1(takeIf2?:"sb")
    }

    //9.repeat　比较ｅａｓｙ
    fun testRepeat(){
        var sum = 0
        repeat(8){
            sum += it
            println1("$it")
        }
    }

    //10 lazy
    fun testLazy(){
        val str : String by lazy {
            ""
        }
    }

    fun println1(str: String) {
        Log.d("ceshi", str)
    }
}