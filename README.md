# Image-Translator-Application
Image translating mobile application using PyQt5.

A mobile application used to translate different words from different languages through image. You can translate text in photos you've already taken. As well as taking a new photo in the Translate app and translate text on the captured photo.

• Mobile App that can be used to create desktop user interfaces and mobile applications on both iOS and Android using PyQt5 python library.

• Optical Character Recognition (OCR) that extracts text from images so that it can be edited, copied, formatted, indexed, searched, or translated.

• A multi-language translation service developed by GoogleTrans Python Library to translate text from one language to another covering 100+ languages.


Prerequisites:

-Python 3 (Go to https://www.python.org/downloads/)

-Download Tesseract (Go to https://github.com/UB-Mannheim/tesseract/wiki and download the latest installer (version 5 at the time). Next, go to https://tesseract.patagames.com/langs/ and select the language file(s) you need if you are working with non-English language material.)
 Note: tesseract path should be added to your .py file
  pytesseract.pytesseract.tesseract_cmd = r'C:\Your_Path\Tesseract-OCR\tesseract.exe'

 -PyQt5 (-- pip install pyqt5)
 
 -GoogleTrans Library (-- pip install GoogleTrans)
