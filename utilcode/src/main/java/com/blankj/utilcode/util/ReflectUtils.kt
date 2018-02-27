package com.blankj.utilcode.util

import java.lang.reflect.*
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/12/15
 * desc  : 反射相关工具类
</pre> *
 */
class ReflectUtils private constructor(private val type: Class<*>, private val any: Any = type) {

    /**
     * 实例化反射对象
     *
     * @param args 实例化需要的参数
     * @return [ReflectUtils]
     */
    @JvmOverloads
    fun newInstance(vararg args: Any = arrayOf()): ReflectUtils {
        val types = getArgsType(*args)
        try {
            val constructor = type().getDeclaredConstructor(*types)
            return newInstance(constructor, *args)
        } catch (e: NoSuchMethodException) {
            val list = type().declaredConstructors.filter { match(it.parameterTypes, types) }
            if (list.isEmpty()) {
                throw ReflectException(e)
            } else {
                sortConstructors(list)
                return newInstance(list[0], *args)
            }
        }

    }

    private fun getArgsType(vararg args: Any?): Array<Class<*>> {
        if (args.isEmpty()) return arrayOf()
        val result = arrayOfNulls<Class<*>>(args.size)
        for (i in args.indices) {
            val value = args[i]
            result[i] = value?.javaClass ?: NULL::class.java
        }
        return result as Array<Class<*>>
    }

    private fun sortConstructors(list: List<Constructor<*>>) {
        Collections.sort(list, Comparator { o1, o2 ->
            val types1 = o1.parameterTypes
            val types2 = o2.parameterTypes
            val len = types1.size
            for (i in 0 until len) {
                if (types1[i] != types2[i]) {
                    return@Comparator if (wrapper(types1[i])!!.isAssignableFrom(wrapper(types2[i])!!)) {
                        1
                    } else {
                        -1
                    }
                }
            }
            0
        })
    }

    private fun newInstance(constructor: Constructor<*>, vararg args: Any): ReflectUtils {
        try {
            return ReflectUtils(
                    constructor.declaringClass,
                    accessible(constructor)!!.newInstance(*args)
            )
        } catch (e: Exception) {
            throw ReflectException(e)
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // field
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置反射的字段
     *
     * @param name 字段名
     * @return [ReflectUtils]
     */
    fun field(name: String): ReflectUtils {
        try {
            val field = getField(name)
            return ReflectUtils(field.type, field.get(any))
        } catch (e: IllegalAccessException) {
            throw ReflectException(e)
        }

    }

    /**
     * 设置反射的字段
     *
     * @param name  字段名
     * @param value 字段值
     * @return [ReflectUtils]
     */
    fun field(name: String, value: Any): ReflectUtils {
        try {
            val field = getField(name)
            field.set(any, unwrap(value))
            return this
        } catch (e: Exception) {
            throw ReflectException(e)
        }

    }

    @Throws(IllegalAccessException::class)
    private fun getField(name: String): Field {
        val field = getAccessibleField(name)
        if (field!!.modifiers and Modifier.FINAL == Modifier.FINAL) {
            try {
                val modifiersField = Field::class.java.getDeclaredField("modifiers")
                modifiersField.isAccessible = true
                modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
            } catch (ignore: NoSuchFieldException) {
                // runs in android will happen
            }

        }
        return field
    }

    private fun getAccessibleField(name: String): Field? {
        var type: Class<*>? = type()
        try {
            return accessible(type!!.getField(name))
        } catch (e: NoSuchFieldException) {
            do {
                try {
                    return accessible(type!!.getDeclaredField(name))
                } catch (ignore: NoSuchFieldException) {
                }

                type = type!!.superclass
            } while (type != null)
            throw ReflectException(e)
        }

    }

    private fun unwrap(any1: Any): Any {
        return (any1 as? ReflectUtils)?.get() ?: any1
    }

    /**
     * 设置反射的方法
     *
     * @param name 方法名
     * @param args 方法需要的参数
     * @return [ReflectUtils]
     * @throws ReflectException 反射异常
     */
    @Throws(ReflectUtils.ReflectException::class)
    @JvmOverloads
    fun method(name: String, vararg args: Any = arrayOf()): ReflectUtils {
        val types = getArgsType(*args)
        try {
            val method = exactMethod(name, types)
            return method(method, obj = any, args = *args)
        } catch (e: NoSuchMethodException) {
            try {
                val method = similarMethod(name, types)
                return method(method, obj = any, args = *args)
            } catch (e1: NoSuchMethodException) {
                throw ReflectException(e1)
            }

        }

    }

    private fun method(method: Method, obj: Any, vararg args: Any): ReflectUtils {
        try {
            accessible(method)
            if (method.returnType == Void.TYPE) {
                method.invoke(obj, *args)
                return reflect(obj)
            } else {
                return reflect(method.invoke(obj, *args))
            }
        } catch (e: Exception) {
            throw ReflectException(e)
        }

    }

    @Throws(NoSuchMethodException::class)
    private fun exactMethod(name: String, types: Array<Class<*>>): Method {
        var type: Class<*>? = type()
        try {
            return type!!.getMethod(name, *types)
        } catch (e: NoSuchMethodException) {
            do {
                try {
                    return type!!.getDeclaredMethod(name, *types)
                } catch (ignore: NoSuchMethodException) {
                }

                type = type!!.superclass
            } while (type != null)
            throw NoSuchMethodException()
        }

    }

    @Throws(NoSuchMethodException::class)
    private fun similarMethod(name: String, types: Array<Class<*>>): Method {
        var type: Class<*>? = type()
        val methods = type!!.methods
                .asSequence()
                .filter { isSimilarSignature(it, name, types) }
                .toMutableList()
        if (!methods.isEmpty()) {
            sortMethods(methods)
            return methods[0]
        }
        do {
            type!!.declaredMethods.filterTo(methods) { isSimilarSignature(it, name, types) }
            if (!methods.isEmpty()) {
                sortMethods(methods)
                return methods[0]
            }
            type = type.superclass
        } while (type != null)

        throw NoSuchMethodException("No similar method " + name + " with params "
                + Arrays.toString(types) + " could be found on type " + type() + ".")
    }

    private fun sortMethods(methods: List<Method>) {
        Collections.sort(methods, Comparator { o1, o2 ->
            val types1 = o1.parameterTypes
            val types2 = o2.parameterTypes
            val len = types1.size
            for (i in 0 until len) {
                if (types1[i] != types2[i]) {
                    return@Comparator if (wrapper(types1[i])!!.isAssignableFrom(wrapper(types2[i])!!)) {
                        1
                    } else {
                        -1
                    }
                }
            }
            0
        })
    }

    private fun isSimilarSignature(possiblyMatchingMethod: Method,
                                   desiredMethodName: String,
                                   desiredParamTypes: Array<Class<*>>): Boolean {
        return possiblyMatchingMethod.name == desiredMethodName && match(possiblyMatchingMethod.parameterTypes, desiredParamTypes)
    }

    private fun match(declaredTypes: Array<Class<*>>, actualTypes: Array<Class<*>>): Boolean {
        if (declaredTypes.size == actualTypes.size) {
            return actualTypes.indices.none { actualTypes[it] != NULL::class.java && !wrapper(declaredTypes[it])!!.isAssignableFrom(wrapper(actualTypes[it])!!) }
        } else {
            return false
        }
    }

    private fun <T : AccessibleObject> accessible(accessible: T?): T? {
        if (accessible == null) return null
        if (accessible is Member) {
            val member = accessible as Member?
            if (Modifier.isPublic(member!!.modifiers) && Modifier.isPublic(member.declaringClass.modifiers)) {
                return accessible
            }
        }
        if (!accessible.isAccessible) accessible.isAccessible = true
        return accessible
    }

    private fun type(): Class<*> {
        return type
    }

    private fun wrapper(type: Class<*>?): Class<*>? {
        if (type == null) {
            return null
        } else if (type.isPrimitive) {
            when (type) {
                Boolean::class.javaPrimitiveType -> return Boolean::class.java
                Int::class.javaPrimitiveType -> return Int::class.java
                Long::class.javaPrimitiveType -> return Long::class.java
                Short::class.javaPrimitiveType -> return Short::class.java
                Byte::class.javaPrimitiveType -> return Byte::class.java
                Double::class.javaPrimitiveType -> return Double::class.java
                Float::class.javaPrimitiveType -> return Float::class.java
                Char::class.javaPrimitiveType -> return Char::class.java
                Void.TYPE -> return Void::class.java
                else -> {
                }
            }
        }
        return type
    }

    /**
     * 获取反射想要获取的
     *
     * @param <T> 返回的范型
     * @return 反射想要获取的
    </T> */
    fun <T> get(): T? {
        return any as? T
    }

    override fun hashCode(): Int {
        return any.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is ReflectUtils && any == other.get<Any>()
    }

    override fun toString(): String {
        return any.toString()
    }

    private class NULL

    class ReflectException : RuntimeException {

        constructor(message: String) : super(message) {}

        constructor(message: String, cause: Throwable) : super(message, cause) {}

        constructor(cause: Throwable) : super(cause) {}

        companion object {

            private const val serialVersionUID = 858774075258496016L
        }
    }

    companion object {

        ///////////////////////////////////////////////////////////////////////////
        // reflect
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 设置要反射的类
         *
         * @param className 完整类名
         * @return [ReflectUtils]
         * @throws ReflectException 反射异常
         */
        @Throws(ReflectUtils.ReflectException::class)
        fun reflect(className: String): ReflectUtils {
            return reflect(forName(className))
        }

        /**
         * 设置要反射的类
         *
         * @param className   完整类名
         * @param classLoader 类加载器
         * @return [ReflectUtils]
         * @throws ReflectException 反射异常
         */
        @Throws(ReflectUtils.ReflectException::class)
        fun reflect(className: String, classLoader: ClassLoader): ReflectUtils {
            return reflect(forName(className, classLoader))
        }

        /**
         * 设置要反射的类
         *
         * @param clazz 类的类型
         * @return [ReflectUtils]
         * @throws ReflectException 反射异常
         */
        @Throws(ReflectUtils.ReflectException::class)
        fun reflect(clazz: Class<*>): ReflectUtils {
            return ReflectUtils(clazz)
        }

        /**
         * 设置要反射的类
         *
         * @param `any` 类对象
         * @return [ReflectUtils]
         * @throws ReflectException 反射异常
         */
        @Throws(ReflectUtils.ReflectException::class)
        fun reflect(any: Any?): ReflectUtils {
            return ReflectUtils(any?.javaClass ?: Any::class.java, any!!)
        }

        private fun forName(className: String): Class<*> {
            try {
                return Class.forName(className)
            } catch (e: ClassNotFoundException) {
                throw ReflectException(e)
            }

        }

        private fun forName(name: String, classLoader: ClassLoader): Class<*> {
            try {
                return Class.forName(name, true, classLoader)
            } catch (e: ClassNotFoundException) {
                throw ReflectException(e)
            }

        }
    }
}