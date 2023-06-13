from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from PIL import Image
import pytesseract


@csrf_exempt
def extract_table(request):
    if request.method == 'POST':
        # Get the image from the POST request
        image_file = request.FILES.get('image')
        pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
        myconfig = r"--psm 6 --oem 3"
        text = pytesseract.image_to_string(Image.open(image_file), config=myconfig)

        # Extract the table from the image text
        # table_extractor = TableExtractor()
        # Return the extracted table(s) as JSON
        return JsonResponse({'tables': text})
    # Return an error message if the request method is not POST
    return JsonResponse({'error': 'Invalid request method'})
##python manage.py runserver
