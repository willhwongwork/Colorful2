package com.cp.purecolor2.data

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class XMLParser {
    companion object {
        private val ns: String? = null

        @Throws(IOException::class, XmlPullParserException::class)
        fun parse(input: InputStream): ArrayList<Colors> {
            try {
                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val parser = factory.newPullParser()
                parser.setInput(input, null)
                parser.nextTag()
                return readColors(parser)
            } finally {
                input.close()
            }
        }

        @Throws(IOException::class, XmlPullParserException::class)
        private fun readColors(parser: XmlPullParser): ArrayList<Colors> {
            val colors = ArrayList<Colors>()
            parser.require(XmlPullParser.START_TAG, ns, "colors")
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                val name = parser.name
                if(name == "colors") {
                    val color: Colors = readColor(parser)
                    Log.d("XMLParser", "color: " + color.name)
                    colors.add(color)
                }
            }
            Log.d("XMLParser", "read " + colors.size)
            return colors
        }

        @Throws(IOException::class, XmlPullParserException::class)
        private fun readColor(parser: XmlPullParser): Colors{
            parser.require(XmlPullParser.START_TAG, ns, "colors")
            val id = parser.getAttributeValue(null, "id")
            val hex = parser.getAttributeValue(null, "hex")
            val red = parser.getAttributeValue(null, "red")
            val green = parser.getAttributeValue(null, "green")
            val blue = parser.getAttributeValue(null, "blue")

            val name = readText(parser)

            return Colors(name, hex, arrayListOf(red, green, blue))
        }

        @Throws(IOException::class, XmlPullParserException::class)
        private fun readText(parser: XmlPullParser): String {
            var result = ""
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.text
                parser.nextTag()
            }
            return result
        }

    }
}