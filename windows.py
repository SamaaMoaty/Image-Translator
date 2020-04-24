from PyQt5 import QtWidgets
from PyQt5 import QtGui , QtCore
from PyQt5.QtWidgets import *
from image_capture import ImageCapture
from googletrans import Translator
from PyQt5.QtGui import *

path = ""


try:
    from PIL import Image
except ImportError:
    import Image
import pytesseract


pytesseract.pytesseract.tesseract_cmd = r"C:\Samaa Moaty\4th CSE\Second Term\Image Processing\project\Image project Final\Tesseract-OCR\tesseract.exe"





class TranslatorGUI (QWidget):
    """Subclass of QWidget that serves as the main window and interface for the application.
    """

    def __init__(self) -> None:

        super().__init__()
        #self.app = QtWidgets.QApplication(sys.argv)
        self.window = QtWidgets.QMainWindow()
        self.init_ui()
        self.imageCaptureDelegate = ImageCapture()

    #Samaa
    def init_ui(self) -> None:
        self.__stylingWindowOne()
        take_pic_btn = QtWidgets.QPushButton("Take a Picture", self.window)
        take_pic_btn.clicked[bool].connect(self.__take_picture)
        take_pic_btn.setGeometry(50,370,180,40)
        take_pic_btn.setStyleSheet("background-color: #008B8B ; font : 12px ")
        take_pic_btn.setIcon(QtGui.QIcon("Camera.png"))
        slct_img_btn = QPushButton("Select an Existing Image", self.window)
        slct_img_btn.clicked[bool].connect(self.__select_existing_image)
        slct_img_btn.setGeometry(50,420,180,40)
        slct_img_btn.setStyleSheet("background-color: #008B8B ; font : 12px")
        slct_img_btn.setIcon(QtGui.QIcon("image.png"))

        self.window.show()

    #Samaa :Styling function
    def __stylingWindowOne (self):
        self.window.setWindowIcon(QtGui.QIcon("home.png"))
        self.window.setWindowTitle("Global Lens")
        self.window.setGeometry(400, 100, 300, 500)  # Samaa
        self.window.setStyleSheet("background-color:#d6d2d2")

        welcome_label = QLabel("Welcome to Global Lens.\n"
                               "Your Image Translator App!" , self.window)
        welcome_label.setFont(QtGui.QFont("Times", 15, QtGui.QFont.Bold))
        welcome_label.setFixedWidth(300)
        welcome_label.setAlignment(QtCore.Qt.AlignLeft)
        welcome_label.setWordWrap(True)
        welcome_label.setGeometry(20,60,140,140)
        logo_label = QtWidgets.QLabel(self.window)
        logo_label.setGeometry(30, 170, 400, 100)
        logo = QtGui.QPixmap('logo.png')
        logo2 =logo.scaled(250,70)
        logo_label.setPixmap(logo2)
        self.resize(logo.width(),logo.height())

    def __take_picture(self) -> None:
        """Launches image capture window, allows user to take image, then loads it.
        """

        image_file_name = self.imageCaptureDelegate.capture_image()
        global path

        path = image_file_name
        print(path)
        self.ImageWindow()


    def __select_existing_image(self) -> None:
        """Launches file dialog box, allows user to select an existing image, then loads it.
        """
        file_dialog = QFileDialog()

        image_file_name  = file_dialog.getOpenFileName()
        global path
        path = image_file_name[0]
        self.ImageWindow()

    def ImageWindow(self):
        self.window1 = ImageWindow()
        self.window.hide()





class ImageWindow(QWidget):

    def __init__(self):
        super().__init__()

        self.window1 = QtWidgets.QMainWindow()
        self.window1.setWindowTitle("Image")
        self.window1.setWindowIcon(QtGui.QIcon("image.png"))
        self.window1.setGeometry(400, 100, 300, 500)  # Samaa
        self.window1.setStyleSheet("background-color:#d6d2d2")

        global path
        global src_lang
        global target_lang
        img = QtWidgets.QLabel(self.window1)
        img.setGeometry(15, -125, 500, 700)
        logo = QtGui.QPixmap(path)
        logo2 = logo.scaled(270, 400)
        img.setPixmap(logo2)
        self.resize(logo.width(), logo.height())

        translate_btn = QPushButton("Translate!",self.window1)
        translate_btn.clicked.connect(self.openSecondDialog)
        translate_btn.setGeometry(20, 450, 120, 40)
        translate_btn.setStyleSheet("background-color: #008B8B ; font : 14px ")
        translate_btn.setIcon(QtGui.QIcon("translate.png"))
        back_btn = QPushButton(" Back" , self.window1)
        back_btn.clicked.connect(self.goBack)
        back_btn.setGeometry(155, 450, 120, 40)
        back_btn.setStyleSheet("background-color: #008B8B ; font : 14px")
        back_btn.setIcon(QtGui.QIcon("back.png"))
        self.window1.show()

    def goBack(self):
        self.window = TranslatorGUI()
        self.window1.hide()

    def openSecondDialog(self):
        global languages
        languages = QDialog()
        languages.setGeometry(450, 200, 200, 200)
        languages.setWindowTitle("Languages")
        languages.setWindowIcon(QtGui.QIcon("translate.png"))
        languages.setModal(True)

        global listoflang
        listoflang =[' Select Language...', 'Afrikaans', 'Irish', 'Albanian', 'Italian', 'Arabic', 'Japanese', 'Azerbaijani',
             'Kannada', 'Basque', 'Korean', 'Bengali', 'Latin', 'Belarusian', 'Latvian',
             'Bulgarian', 'Lithuanian', 'Catalan', 'Macedonian', 'Chinese Simplified', 'Malay',
             'Chinese Traditional', 'Maltese', 'Croatian', 'Norwegian', 'Czech', 'Persian',
             'Danish', 'Polish', 'Dutch', 'Portuguese', 'English', 'Romanian', 'Esperanto',
             'Russian', 'Estonian', 'Serbian', 'Filipino', 'Slovak', 'Finnish', 'Slovenian',
             'French', 'Spanish', 'Galician', 'Swahili', 'Georgian', 'Swedish', 'German',
             'Tamil', 'Greek', 'Telugu', 'Gujarati', 'Thai', 'Haitian Creole', 'Turkish',
             'Hebrew', 'Ukrainian', 'Hindi', 'Urdu', 'Hungarian', 'Vietnamese', 'Icelandic',
             'Welsh', 'Indonesian', 'Yiddish']
        listoflang.sort()

        src__lang = QLabel("Source Language : ", languages)
        src__lang.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
        src__lang.setFixedWidth(200)
        src__lang.setAlignment(QtCore.Qt.AlignLeft)
        src__lang.setWordWrap(True)
        src__lang.move(10, 20)

        global select_src_language_box
        select_src_language_box = QComboBox(languages)
        select_src_language_box.move(10, 50)
        select_src_language_box.setFixedWidth(150)
        select_src_language_box.setFont(QtGui.QFont("Times", 12))
        select_src_language_box.addItems(listoflang)
        select_src_language_box.setEditable(True)
        select_src_language_box.setInsertPolicy(QComboBox.NoInsert)


        target__lang = QLabel("Target Language : ", languages)
        target__lang.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
        target__lang.setFixedWidth(200)
        target__lang.setAlignment(QtCore.Qt.AlignLeft)
        target__lang.setWordWrap(True)
        target__lang.move(10, 90)

        global select_target_language_box
        select_target_language_box = QComboBox(languages)
        select_target_language_box.move(10, 120)
        select_target_language_box.setFixedWidth(150)
        select_target_language_box.setFont(QtGui.QFont("Times", 12))
        select_target_language_box.addItems(listoflang)
        select_target_language_box.setEditable(True)
        select_target_language_box.setInsertPolicy(QComboBox.NoInsert)


        select_src_language_box.currentIndexChanged.connect(
            lambda x: self.test1(select_src_language_box.currentText()))
        select_target_language_box.currentIndexChanged.connect(
            lambda x: self.test2(select_target_language_box.currentText()))


        ok = QPushButton("OK" , languages)
        ok.move(130,160)
        ok.setFixedSize(50,30)
        ok.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
        #ok.clicked.connect(languages.close)
        ok.clicked.connect(self.translateSara)

        QWidget.setFocus(ok)

        languages.exec()

    def test1(self, src):
        global src_lang
        src_lang = src


    def test2(self, target):
        global target_lang
        target_lang = target

    def translateSara(self):
        global target_lang
        global src_lang

        if select_src_language_box.currentIndex() == 0 and select_target_language_box.currentIndex() == 0:
            src_lang = "English"
            target_lang = "English"
            error = QDialog()
            error.setWindowTitle("Error")
            error.setGeometry(450, 200, 200, 200)
            widget1 = QLabel("Both Language is not specified.\n\n Your default language is English.", error)
            widget1.setWordWrap(True)
            widget1.setFixedWidth(180)
            widget1.move(10, 30)
            widget1.setFont(QtGui.QFont("Times", 11, QtGui.QFont.Bold))

            widget2 = QPushButton("OK", error)
            widget2.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget2.clicked.connect(error.close)
            widget2.clicked.connect(self.transition)
            widget2.move(20, 150)
            widget2.setFixedSize(70, 40)
            widget2.clicked.connect(languages.close)

            widget3 = QPushButton("Edit", error)
            widget3.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget3.move(100, 150)
            widget3.setFixedSize(70, 40)
            widget3.clicked.connect(error.close)
            error.exec()

        elif select_src_language_box.currentIndex() == 0:
            src_lang = "English"
            error_src = QDialog()
            error_src.setWindowTitle("Error")
            error_src.setGeometry(450, 200, 200, 200)
            widget1 = QLabel("Source Language is not specified.\n\n Your default language is English.", error_src)
            widget1.setWordWrap(True)
            widget1.setFixedWidth(180)
            widget1.move(10, 30)
            widget1.setFont(QtGui.QFont("Times", 11, QtGui.QFont.Bold))

            widget2 = QPushButton("OK", error_src)
            widget2.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget2.clicked.connect(error_src.close)
            widget2.clicked.connect(self.transition)
            widget2.move(20, 150)
            widget2.setFixedSize(70,40)
            widget2.clicked.connect(languages.close)

            widget3 = QPushButton("Edit", error_src)
            widget3.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget3.move(100, 150)
            widget3.setFixedSize(70,40)
            widget3.clicked.connect(error_src.close)
            error_src.exec()

        elif select_target_language_box.currentIndex() == 0:
            target_lang="English"
            error_target = QDialog()
            error_target.setWindowTitle("Error")
            error_target.setGeometry(450, 200, 200, 200)
            widget1 = QLabel("Target Language is not specified.\n\n Your default language is English.", error_target)
            widget1.setWordWrap(True)
            widget1.setFixedWidth(180)
            widget1.move(10, 30)
            widget1.setFont(QtGui.QFont("Times", 11, QtGui.QFont.Bold))

            widget2 = QPushButton("OK", error_target)
            widget2.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget2.clicked.connect(error_target.close)
            widget2.clicked.connect(self.transition)
            widget2.move(20, 150)
            widget2.setFixedSize(70, 40)
            widget2.clicked.connect(languages.close)

            widget3 = QPushButton("Edit", error_target)
            widget3.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget3.move(100, 150)
            widget3.setFixedSize(70, 40)
            widget3.clicked.connect(error_target.close)
            error_target.exec()

        else:
            languages.close()
            self.transition()

    def transition (self):
        self.window2 = TranslatedWindow()
        self.window1.hide()

class TranslatedWindow(QWidget):
    def error(self):
        mydialog = QDialog()
        mydialog.setWindowTitle("Error")
        mydialog.setGeometry(450, 200, 200, 200)
        widget1 = QLabel("No Text Detected", mydialog)
        widget2 = QPushButton("Try Again", mydialog)
        widget1.move(40, 80)
        widget1.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
        widget2.move(110, 160)
        widget2.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
        widget2.clicked.connect(mydialog.close)
        widget2.clicked.connect(self.goBack)

        mydialog.exec()


    def __init__(self):
        super().__init__()

        self.window2 = QtWidgets.QMainWindow()
        self.window2.setWindowTitle("Translator")
        self.window2.setWindowIcon(QtGui.QIcon("translate.png"))
        self.window2.setGeometry(400, 100, 300, 500)  # Samaa
        self.window2.setStyleSheet("background-color:#d6d2d2")

        global trans_text

        trans_language_codes = {'Afrikaans': 'af', 'Irish': 'ga', 'Albanian': 'sq', 'Italian': 'it', 'Arabic': 'ar',
                          'Japanese': 'ja', 'Azerbaijani': 'az',
                          'Kannada': 'kn', 'Basque': 'eu', 'Korean': 'ko', 'Bengali': 'bn', 'Latin': 'la',
                          'Belarusian': 'be', 'Latvian': 'lv',
                          'Bulgarian': 'bg', 'Lithuanian': 'lt', 'Catalan': 'ca', 'Macedonian': 'mk',
                          'Chinese Simplified': 'zh-CN', 'Malay': 'ms',
                          'Chinese Traditional': 'zh-TW', 'Maltese': 'mt', 'Croatian': 'hr', 'Norwegian': 'no',
                          'Czech': 'cs', 'Persian': 'fa',
                          'Danish': 'da', 'Polish': 'pl', 'Dutch': 'nl', 'Portuguese': 'pt', 'English': 'en',
                          'Romanian': 'ro', 'Esperanto': 'eo',
                          'Russian': 'ru', 'Estonian': 'et', 'Serbian': 'sr', 'Filipino': 'tl', 'Slovak': 'sk',
                          'Finnish': 'fi', 'Slovenian': 'sl',
                          'French': 'fr', 'Spanish': 'es', 'Galician': 'gl', 'Swahili': 'sw', 'Georgian': 'ka',
                          'Swedish': 'sv', 'German': 'de',
                          'Tamil': 'ta', 'Greek': 'el', 'Telugu': 'te', 'Gujarati': 'gu', 'Thai': 'th',
                          'Haitian Creole': 'ht', 'Turkish': 'tr',
                          'Hebrew': 'iw', 'Ukrainian': 'uk', 'Hindi': 'hi', 'Urdu': 'ur', 'Hungarian': 'hu',
                          'Vietnamese': 'vi', 'Icelandic': 'is',
                          'Welsh': 'y', 'Indonesian': 'id', 'Yiddish': 'yi'}

        OCR_language_codes = {'Afrikaans': 'afr', 'Irish': 'gle', 'Albanian': 'sqi', 'Italian': 'ita', 'Arabic': "ara",
                                 'Japanese': 'jpn', 'Azerbaijani': 'aze',
                                 'Kannada': 'kan', 'Basque': 'eus', 'Korean': 'kor', 'Bengali': 'ben', 'Latin': 'lat',
                                 'Belarusian': 'bel', 'Latvian': 'lav',
                                 'Bulgarian': 'bul', 'Lithuanian': 'lit', 'Catalan': 'cat', 'Macedonian': 'mkd',
                                 'Chinese Simplified': 'chi_sim', 'Malay': 'msa',
                                 'Chinese Traditional': 'chi_tra', 'Maltese': 'mlt', 'Croatian': 'hrv', 'Norwegian': 'nor',
                                 'Czech': 'ces', 'Persian': 'fas',
                                 'Danish': 'dan', 'Polish': 'pol', 'Dutch': 'nld', 'Portuguese': 'por', 'English': 'eng',
                                 'Romanian': 'ron', 'Esperanto': 'epo',
                                 'Russian': 'rus', 'Estonian': 'est', 'Serbian': 'srp', 'Filipino': 'tgl', 'Slovak': 'slk',
                                 'Finnish': 'fin', 'Slovenian': 'slv',
                                 'French': 'fra', 'Spanish': 'spa', 'Galician': 'glg', 'Swahili': 'swa', 'Georgian': 'kat',
                                 'Swedish': 'swe', 'German': 'deu',
                                 'Tamil': 'tam', 'Greek': 'ell', 'Telugu': 'tel', 'Gujarati': 'guj', 'Thai': 'tha',
                                 'Haitian Creole': 'hat', 'Turkish': 'tur',
                                 'Hebrew': 'heb', 'Ukrainian': 'ukr', 'Hindi': 'hin', 'Urdu': 'urd', 'Hungarian': 'hun',
                                 'Vietnamese': 'vie', 'Icelandic': 'isl',
                                 'Welsh': 'cym', 'Indonesian': 'ind', 'Yiddish': 'yid'}



        src_lang_OCR = OCR_language_codes[src_lang]
        global detected_text
        detected_text = self.ocr_core(path, src_lang_OCR)

        if detected_text is "":
            self.error()
        else:
            src_lang_ = trans_language_codes[src_lang]
            target_lang_ = trans_language_codes[target_lang]
            trans_text = self.translate(detected_text, src_lang_, target_lang_)

            widget1 = QLabel("Detected Text : ", self.window2)
            widget1.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))
            widget1.setFixedWidth(200)
            widget1.setAlignment(QtCore.Qt.AlignLeft)
            widget1.setWordWrap(True)
            widget1.move(10, 20)

            self.textbox =QPlainTextEdit(self.window2)
            self.textbox.move(15, 45)
            self.textbox.setFixedSize(270, 180)
            self.textbox.setFont(QtGui.QFont("Times", 14 ))
            self.textbox.setStyleSheet("background-color:#fff")
            self.textbox.appendPlainText(detected_text)

            widget2 = QLabel("Translated Text : ", self.window2)
            widget2.setFont(QtGui.QFont("Times", 12, QtGui.QFont.Bold))

            widget2.setFixedWidth(200)
            widget2.setAlignment(QtCore.Qt.AlignLeft)
            widget2.setWordWrap(True)
            widget2.move(15,240)

            self.textbox = QPlainTextEdit(self.window2)
            self.textbox.move(15, 265)
            self.textbox.setFixedSize(270, 180)
            self.textbox.setFont(QtGui.QFont("Times", 14))
            self.textbox.setStyleSheet("background-color:#fff")
            self.textbox.appendPlainText(trans_text)

            back_btn = QPushButton("Done", self.window2)
            back_btn.clicked.connect(self.goBack)
            back_btn.setGeometry(155, 450, 120, 40)
            back_btn.setStyleSheet("background-color: #008B8B ; font : 14px")
            back_btn.setIcon(QtGui.QIcon("done.png"))

            self.window2.show()

    def goBack(self):
        self.window = TranslatorGUI()
        self.window2.hide()

    def ocr_core(self, filename,src_lang_OCR):
        """
        This function will handle the core OCR processing of images.
        """
        config = ("-l " + src_lang_OCR)
        text = pytesseract.image_to_string(Image.open(filename),config=config)  # We'll use Pillow's Image class to open the image and pytesseract to detect the string in the image
        return text

    def translate(self, text, source, destination):
        translator = Translator()
        trans_text = translator.translate(text, src=source, dest=destination).text
        # print(trans_text)
        return trans_text
