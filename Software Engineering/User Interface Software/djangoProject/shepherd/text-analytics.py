key = "13bc24274d694152a366f9868a1875da"
endpoint = "https://text-analytics5774.cognitiveservices.azure.com/"

def language_detection_example(client):
    try:
        documents = ["Ce document est rédigé en Français."]
        response = client.detect_language(documents=documents, country_hint='us')[0]
        print("Language: ", response.primary_language.name)

    except Exception as err:
        print("Encountered exception. {}".format(err))


language_detection_example(client)
