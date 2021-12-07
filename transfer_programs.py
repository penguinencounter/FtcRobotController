import requests
import json
import os
import re

ROBOT_HTTP_ADDR = "http://192.168.43.1:8080/"
CODE_DIR = "./TeamCode/src/main/java/org/firstinspires/ftc/teamcode"

FILE_LIST_PATH = "java/file/tree"

def get_files_on_server(robot_url, list_path):
    full_url = robot_url + list_path
    download = requests.get(full_url)
    return json.loads(download.text)['src']


def get_files_to_remove(code_dir_path: str, remote_src: list):
    files = os.listdir(code_dir_path)
    remote_pathfix = ""
    for path in remote_src:
        match = re.match(r'(.*\/).*\.java', path)
        if match is None:
            continue
        remote_pathfix = match.group(1)
    need_to_remove = []
    for file in files:
        if remote_pathfix+file in remote_src:
            need_to_remove.append(remote_pathfix+file)
    need_to_transfer = []
    for file in files:
        match = re.match(r'(.*\/).*\.java', file)
        if match:
            need_to_transfer.append(file)
    return need_to_transfer, need_to_remove


def delete_file(robot_url: str, remote_path: str):
    target = "java/file/delete"
    requests.post(robot_url+target, data={"delete": [remote_path]})


def upload_file(robot_url: str, local_path: str):
    target = "java/file/upload"
    with open(local_path) as f:
        print('Uploading: {local_path}')
        requests.post(robot_url+target, files={"file": f})
    


if __name__ == "__main__":
    print('Connecting and downloading file list...')
    files = get_files_on_server(ROBOT_HTTP_ADDR, FILE_LIST_PATH)
    print(files)
    transfer, remove = get_files_to_remove(CODE_DIR, files)
    print(remove)
    input("Press enter to continue with transfer...")
    for file in remove:
        delete_file(ROBOT_HTTP_ADDR, file)
    for file in transfer:
        upload_file(ROBOT_HTTP_ADDR, file)
    print("Doneso")
    
