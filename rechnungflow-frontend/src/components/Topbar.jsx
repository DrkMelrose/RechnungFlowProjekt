import {Bell} from "lucide-react";
import {MenuIcon} from "lucide-react";
import Dashboard from "../pages/Dashboard.jsx";
import {useLocation} from "react-router-dom";


const pageTitles = {
    "/":"Dashboard",
    "/clients":"Clients",
    "/employees":"Employees",
    "/objects":"Objects",
    "/worklogs":"Worklogs",
    "/invoices":"Invoices",
    "/generate-invoice":"Generate Invoice",
    "/settings":"Settings",
}

function Topbar({onMenuClick}) {
    const location = useLocation();

    const title = pageTitles[location.pathname] || Dashboard;

    return (
        <header className="h-16 border-b border-slate-200 bg-white/80 backdrop-blur flex items-center justify-between px-5 lg:px-7 sticky top-0 z-10">
            <div className="flex items-center gap-4">
                <button onClick={onMenuClick}
                        className="text-slate-500 hover:text-slate-900">
                    <MenuIcon size={22} />
                </button>
                <div>
                    <p className="text-xs text-slate-500">Cleaning business platform</p>
                    <h1 className="text-lg font-bold text-slate-900">{title}</h1>
                </div>
            </div>

            <div className="flex items-center gap-4">
                <button className="relative rounded-xl p-2 hover:bg-slate-100 text-slate-600">
                    <Bell size={19} />
                    <span className="absolute right-2 top-2 h-2 w-2 rounded-full bg-red-500" />
                </button>
                <div className="flex items-center gap-3">
                    <div className="h-9 w-9 rounded-full bg-slate-200 flex items-center justify-center text-sm font-semibold text-slate-700">
                        IS
                    </div>
                    <div className="hidden sm:block">
                        <p className="text-sm font-semibold text-slate-900">Admin</p>
                        <p className="text-xs text-slate-500">Ivan</p>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Topbar;