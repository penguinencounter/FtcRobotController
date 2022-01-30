import requests
import json
import os
import re
from hashlib import sha256
import time
# ROBOT_HTTP_ADDR = 'http://'+input('Enter IP and Port for Robot Controller Console: ')+'/'
ROBOT_HTTP_ADDR = 'http://192.168.43.1:8080/'  #variable
CODE_DIR = "./TeamCode/src/main/java/org/firstinspires/ftc/teamcode/"

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
        match = re.match(r'.*\.java', file)
        if match:
            need_to_transfer.append(file)
    return need_to_transfer, need_to_remove


def delete_file(robot_url: str, remote_path: str):
    target = "java/file/delete"
    print(f'Deleting: src{remote_path} via {robot_url+target}')
    payload={'delete': f'["src{remote_path}"]'}
    r = requests.post(robot_url+target, data=payload)
    print(r.request.body)
    return r.text


def upload_file(robot_url: str, local_path: str):
    target = "java/file/upload"
    with open(local_path, 'rb') as f:
        print(f'Uploading: {local_path} via {robot_url+target}')
        files = {"file": (local_path.split('/')[-1], f, 'application/octet-stream')}
        print(files)
        r = requests.post(robot_url+target, files=files)
    return r.text

def get_hash(target_file: str):
    actual = CODE_DIR + target_file
    with open(actual, 'rb') as f:
        d = f.read()
    hash = sha256(d).hexdigest()
    return hash


if __name__ == "__main__":
    print('Connecting and downloading file list...')
    files = get_files_on_server(ROBOT_HTTP_ADDR, FILE_LIST_PATH)
    print(files)
    transfer, remove = get_files_to_remove(CODE_DIR, files)
    # files = ['src/main/test/Test.java', 'src/main/test/Test3.java', 'src/main/test/WOW.java']
    # transfer = ['Test2.java', 'Test.java']
    # remove = ['src/main/test/Test.java']
    files_vis = [x.split('/')[-1] for x in files]
    print('CHANGES:')
    print('Remote'.ljust(50, '-')+' <-- ' + 'Local'.ljust(50, '-'))
    for x in range(max(len(files), len(transfer))):
        if x < len(files_vis):
            print((f'[delete] {files_vis[x]}' if files[x] in remove else f'{files_vis[x]}').ljust(50), end='')
        else:
            print(' '*50, end='')
        print('     ', end='')
        if x < len(transfer):
            h = get_hash(transfer[x])
            tf = f'{transfer[x]} ({h})'
            print(tf.ljust(50))
        else:
            print(' '*50)
    input("Press enter to continue with transfer...")
    for file in remove:
        print(delete_file(ROBOT_HTTP_ADDR, file))
    for file in transfer:
        print(upload_file(ROBOT_HTTP_ADDR, CODE_DIR+file))
    print("Done:")
    for file in transfer:
        print(f'  Uploaded {file} @ {get_hash(file)}')
    print(time.asctime())
    
