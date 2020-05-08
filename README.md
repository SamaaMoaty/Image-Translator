# Image-Translator
Image Translator Application for Desktop and Android Devices

Demo Video: www.youtube.com

>The application is used to translate different words from different languages through image. You can translate text in photos you've already taken. As well as taking a new photo in the Translate app and translate text on the captured photo using Optical Character Recognition (OCR) extracting text from images so that it can be edited, copied, formatted, indexed, searched, or translated covering 100+ languages.


## Demo
   ![AppDemo](Mobile App/IMG_8924.JPG)
   
## Desktop Interface 
   Using PyQt5
   >Import Desktop Interface Folder in PyCharm

### Prerequisities:

- Python 3
  >Go to https://www.python.org/downloads/
- Download Tesseract 
  >Go to https://github.com/UB-Mannheim/tesseract/wiki and download the latest installer (version 5 at the time). Next, go to https://tesseract.patagames.com/langs/ and select the language file(s) you need if you are working with non-English language material.
  
  > Note: tesseract path should be added to your .py file:   pytesseract.pytesseract.tesseract_cmd = r'C:\Your_Path\Tesseract-OCR\tesseract.exe'

- PyQt5 
  >-- pip install pyqt5

- GoogleTrans Library 
  >-- pip install GoogleTrans

## Mobile Application 
  Using Java 
  >Import Mobile App Folder in Android Studio

