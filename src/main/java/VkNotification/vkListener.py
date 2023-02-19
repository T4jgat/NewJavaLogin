import vk_api
import zmq
import random

from vk_api.longpoll import VkLongPoll, VkEventType

TOKEN = ''
USER_ID = 0

# Initialize the VK API session
vk_session = vk_api.VkApi(token=TOKEN)

# Set up the long polling connection
longpoll = VkLongPoll(vk_session)

# Initialize the ZeroMQ context and socket
context = zmq.Context()
socket = context.socket(zmq.SUB)
socket.setsockopt_string(zmq.SUBSCRIBE, '')

# Connect to the ZeroMQ messaging service
socket.connect('tcp://127.0.0.1:5555')


# Function to send a message to the user
# Function to send a message to the user
def send_message(text):
    random_id = random.getrandbits(31)
    vk_session.method('messages.send', {'user_id': USER_ID, 'message': text, 'random_id': random_id})


# Main loop to check for updates and send messages
for event in longpoll.listen():
    # Check for messages from the messaging service
    while (True):
        try:
            message = socket.recv_string(zmq.DONTWAIT)
            # Replace with your condition to trigger the notification
            if message == 'true':
                send_message('I have been logged in system!')
        except zmq.Again:
            break

