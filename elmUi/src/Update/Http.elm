module Update.Http exposing (..)

import Http exposing (..)
import Model exposing (..)
import HttpUtils exposing (..)
import Msg exposing (..)

fetchData : Cmd Msg
fetchData =
    let
      url = "/ad-replacement"
    in
      Http.send Fetch (Http.get url adReplacementSettingsDecoder)


putRequest : AdReplacementEntry -> Request ()
putRequest entry =
    Http.request
      { method = "PUT"
      , headers = [(Http.header "Content-Type" "application/json")]
      , url = "/ad-replacement"
      , body = adReplacementEntryEncoder entry |> Http.jsonBody
      , expect = expectStringResponse (\_ -> Ok ())
      , timeout = Nothing
      , withCredentials = False
      }


submitChoices : AdReplacementEntry -> Cmd Msg
submitChoices entry =
    let
     x = putRequest entry
     y = Debug.log "request is " (x)
    in
    putRequest entry
      |> Http.send UpdateChoices
