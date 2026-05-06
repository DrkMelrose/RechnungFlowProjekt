import {navItems} from "../data/mockData.js";
import {ReceiptText} from "lucide-react";

function Sidebar() {
    return (
        <aside className="w-64 border-r border-slate-200 bg-white px-5 py-6 hidden lg:block">
            <div className="flex items-center gap-3 mb-10">
                <div className="h-9 w-9 rounded-2xl bg-blue-600 flex items-center justify-center text-white shadow-sm">
                    <ReceiptText size={20} />
                </div>
                <span className="font-bold text-slate-900 tracking-tight">RechnungFlow</span>
            </div>

            <nav className="space-y-1">
                {navItems.map((item) => {
                    const Icon = item.icon;
                    return (
                        <button
                            key={item.label}
                            className={`w-full flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm transition ${
                                item.active
                                    ? "bg-blue-50 text-blue-700 font-semibold"
                                    : "text-slate-600 hover:bg-slate-50 hover:text-slate-900"
                            }`}
                        >
                            <Icon size={17} />
                            {item.label}
                        </button>
                    );
                })}
            </nav>
        </aside>
    );
}

export default Sidebar;