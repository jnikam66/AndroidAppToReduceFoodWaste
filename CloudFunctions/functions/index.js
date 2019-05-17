
//import firebase functions modules
const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// Listens for new messages added to messages/:pushId
exports.pushNotification = functions.database.ref('/posts/{pushId}').onWrite( (change, context) => {

    console.log('Push notification event triggered');
	
    //  Grab the current value of what was written to the Realtime Database.
	const snapshot = change.after;
	const newPost = snapshot.val();
	
	const payload = {notification: {
         title: 'New post',
         body: `${newPost.title}`
         }
     };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };


    return admin.messaging().sendToTopic("pushNotifications", payload, options);
});

