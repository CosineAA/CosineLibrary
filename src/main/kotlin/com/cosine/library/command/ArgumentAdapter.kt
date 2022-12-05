package com.cosine.library.command

class ArgumentAdapter<T>(
    clazz: Class<T>,
    val label: String,
    private val castable: Castable<T>,
    val tabCompleter: ()->List<String>? = { null }
) {

    companion object {
        private val argumentMap = HashMap<String, ArgumentAdapter<*>>()
        inline operator fun <reified T>invoke(label: String, castable: Castable<T>, noinline tabCompleter: () -> List<String>? = { null }) =
            ArgumentAdapter(T::class.java, label, castable, tabCompleter)
        internal fun getArgument(string: String?): ArgumentAdapter<*>? = if(string == null) null else argumentMap[string]
        internal fun registerAdapter(adapter: ArgumentAdapter<*>) { argumentMap[adapter.genericName] = adapter }
    }

    private val genericName = clazz.simpleName
    val genericType = clazz
    fun cast(string: String?): T? = castable.cast(string)

}