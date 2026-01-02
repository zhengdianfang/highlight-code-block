package com.zhengdianfang.highlightr

interface Emitter {
    fun addText(text: String)
    fun startScope(scope: String)
    fun endScope()
    fun finalize()
    fun getResult(): Any?
}

class TokenTreeEmitter : Emitter {
    val events = mutableListOf<Event>()
    
    sealed class Event {
        data class Text(val text: String) : Event()
        data class Start(val scope: String) : Event()
        object End : Event()
    }

    override fun addText(text: String) {
        events.add(Event.Text(text))
    }

    override fun startScope(scope: String) {
        events.add(Event.Start(scope))
    }

    override fun endScope() {
        events.add(Event.End)
    }

    override fun finalize() {
        // Nothing to do
    }

    override fun getResult(): List<Event> {
        return events
    }
}
