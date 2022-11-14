AuthSecurity

# OAUTH
See https://www.youtube.com/watch?v=3pZ3Nh8tgTE&ab_channel=JavaBrains 
Used for authorization (not authentication) between service.
View it like hotel heys
It give you access to a resource (room) that can be revoked.
Multiple flows
Scenare: Resource owner is accessing photo printing service (client) and wants to print pictures from google photos (Service). They need the photo printing serivce to get access to their google photos.


1.. OAuth Flow 1: Authorization Code Flow
Resource owner requests picture from Service (google)
Client requests token from service (goolge)
Google checks with user if ok
Google issues Auth token to client (photo app)
Client logs in with Auth token and gets access token
Client acceses photos with Access token

1.. OAUTH Flow 1 Implicit FLow
Same as Authorization Code Flow except instead of using Auth token, Service issues Access token directly.
Slightly less secure as access token leakage could cause othewr apps to get access.
The implicit flow is a browser only flow. It is less secure than the Code Flow since it doesn't authenticate the client. But it is still a useful flow in web applications that need access tokens and cannot make use of a backend
Analogy hotel key is mislaid and someone else gets it, and knows room number to access resource. For Flow 1, the key is stored behind the desk and you must authencae yourself to get it.

1.. Client Crentials Flow
OAUTH for authoriztion between Microservices.
#Used when client is trusted e.g. Microservices. 
Microserive 2 has access to DB, and mS1 wants it, and wants to calls api on MS2
MS1 talks to Auth Server and gets Auth token.
AuthSErver used Auth token to get Access token from MS2

# JWT
See https://www.youtube.com/watch?v=7Q17ubqLfaM&ab_channel=WebDevSimplified
See https://jwt.io/ for testing. It uses a number of signature protocols and can verify token (you provide private key)

JSON Web Tokens; used for Authorisation (not Auth)
JWT is mainly used for APIs while OAuth can be used for web, browser, API, and various apps or resources. JWT token vs oauth token: JWT defines a token format while OAuth deals in defining authorization protocols. JWT is simple and easy to learn from the initial stage while OAuth is complex.

Especially good with clusters.. No need dfor clustered session data. Each server can validate jwt token seperately.
Liekwise if there are multiple miroserives serviing a user the same jwt token could potentially be used once they all share the secret key for hashing.

In old systems. User logs in and system stores this fact in the session cookie. User send ssubsequenet requests with session id, nad server validates user is logged in.

In JWT. Server sends back token after user logs in. It doesn't need to store anything in session. JWT token encoded (user and roles maybe) and  signed with private key. So when token is sent back (could be as cookie), server can validate signature and know who user is.

Format is header.data.signature
* Header contains alg (algorithm) and typ (JWT)
* data has stuff like seb, name , iat (issued at, eg. if expire after time) 
* VErify signature: 
base64(header)+.+base64(payload) hashed, and verify it equals <secret>