import fitz  # PyMuPDF
from googletrans import Translator
import os
import re
import sys

def extract_text_from_pdf(pdf_path):
    text = ""
    doc = fitz.open(pdf_path)
    for page_num in range(doc.page_count):
        page = doc.load_page(page_num)
        page_text = page.get_text()
        # Replace problematic characters
        cleaned_text = re.sub(r'[^\x00-\x7F]+', ' ', page_text)
        text += cleaned_text
    return text

def translate_english_to_french(text):
    translator = Translator()

    # Initialize the translated text as an empty string
    translated_text = ""

    try:
        # Attempt to translate the text to French
        translated = translator.translate(text, src='en', dest='fr')
        translated_text = translated.text
    except IndexError:
        # Handle the IndexError (list index out of range) gracefully
        translated_text = "Translation Error: Could not translate the text"

    return text, translated_text

if len(sys.argv) != 2:
    print("Usage: python a.py <pdf_file_path>")
    sys.exit(1)

# Specify the path to the PDF file
pdf_file_path = sys.argv[1]

# Check if the file exists
if os.path.exists(pdf_file_path):
    english_text = extract_text_from_pdf(pdf_file_path)
    original_text, french_translation = translate_english_to_french(english_text)

    print("Original Text:")
    print(original_text)
    print("\nFrench Translation:")
    print(french_translation)
else:
    print("The specified PDF file does not exist.")
