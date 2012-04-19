package nl.kennisnet.arena.client.panel;

import java.util.List;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.event.TeamFilterEvent;
import nl.kennisnet.arena.client.util.GeomUtil;
import nl.kennisnet.arena.client.widget.QuestItemMarker;

import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.Polyline;
import com.google.maps.gwt.client.PolylineOptions;

public class MonitorMapPanel extends AbstractMapPanel implements
		RefreshQuestLogEvent.Handler, TeamFilterEvent.Handler {

	public MonitorMapPanel() {
		super();
		EventBus.get().addHandler(RefreshQuestLogEvent.TYPE, this);
		EventBus.get().addHandler(TeamFilterEvent.TYPE, this);
		triggerZoomForTheFirstTime();
	}

	protected void refresh() {
	  clearMap();
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (questDTO != null && questDTO.getItems() != null) {
         for (QuestItemDTO itemDTO : questDTO.getItems()) {
            QuestItemMarker questItemMarker = new QuestItemMarker(getMapWidget(), itemDTO, true);
            markerObjects.add(questItemMarker.getMarker());
            polygonObjects.add(questItemMarker.getPolygon()[0]);
            polygonObjects.add(questItemMarker.getPolygon()[1]);
         }
      }

      if (questDTO.getBorder() != null) {
         Polygon polygon = Polygon.create();
         polygon.setPath(polygonArrayToMvcArray(GeomUtil.createGWTPolygon(questDTO.getBorder())));
         polygonObjects.add(polygon);
         polygon.setMap(getMapWidget());
      }

      LogDTO log = QuestState.getInstance().getLog();
      if (log!=null){
         List<TeamDTO> teams = log.getTeams();
         // Map<String, List<ActionDTO>> actionLog =
         // QuestState.getInstance().getActionLog();
         for (TeamDTO team : teams) {
            if (QuestState.getInstance().isTeamVisible(team.getName())) {
               List<ActionDTO> teamActions = log.getTeamActions(team.getName());
               final Polyline trail = createTeamTrail(teamActions);
               if (trail != null) {
            	   trail.setOptions(getStyleForTeam(team));
            	   createLastPositionMarker(teamActions).setMap(getMapWidget());
            	   polylineObjects.add(trail);
            	   trail.setMap(getMapWidget());
               }
            }
         }
      }
   }

	private PolylineOptions getStyleForTeam(TeamDTO team) {
		PolylineOptions polylineOptions = PolylineOptions.create();
		polylineOptions.setStrokeColor(team.getColor());
		polylineOptions.setStrokeWeight(2);
		polylineOptions.setStrokeOpacity(1.0);
		return polylineOptions;
	}

	private Marker createLastPositionMarker(List<ActionDTO> actionlog) {
		ActionDTO actionDTO = actionlog.get(actionlog.size() - 1);
		MarkerOptions options = MarkerOptions.create();
		options.setDraggable(false);
		options.setTitle("Laatst bekende locatie team : "
				+ actionDTO.getTeamName());
		Marker result = Marker.create();
		result.setPosition(LatLng.create(actionDTO.getPoint().getLatitude(), actionDTO.getPoint().getLongitude()));
		result.setOptions(options);
		markerObjects.add(result);
		return result;
	}

	private Polyline createTeamTrail(List<ActionDTO> actions) {
		if (actions != null) {
			LatLng[] result = new LatLng[actions.size()];
			for (int i = 0; i < actions.size(); i++) {
				SimplePoint point = actions.get(i).getPoint();
				result[i] = GeomUtil.createGWTPoint(point);
			}
			Polyline polyline = Polyline.create();
			polyline.setPath(polygonArrayToMvcArray(result));
			return polyline;
		}
		return null;
	}

	public MVCArray<LatLng> polygonArrayToMvcArray(LatLng[] latlngArray) {
		MVCArray<LatLng> mvcArray = MVCArray.create();
		for (LatLng latLng : latlngArray) {
			mvcArray.push(latLng);
		}
		return mvcArray;
	}

	@Override
	public void onRefreshQuestLog(RefreshQuestLogEvent p) {
		refresh();
	}

	@Override
	public void onTeamFilter(TeamFilterEvent p) {
		refresh();
	}

	protected int getViewId() {
		return QuestState.MONITOR_VIEW;
	}

	public void resize(int x, int y) {
		super.resize(x, y);
		zoomIfNeeded();
	}

}
